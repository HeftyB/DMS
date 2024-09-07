package com.heftyb.dms.account.services;

import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.account.repositories.PayPeriodRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.exceptions.ResourceFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "payPeriodService")
@Transactional
public class PayPeriodServiceImp implements PayPeriodService {

    private final PayPeriodRepository payPeriodRepo;

    public PayPeriodServiceImp(final PayPeriodRepository payPeriodRepository) {
        payPeriodRepo = payPeriodRepository;
    }

    @Override
    public List<PayPeriod> findAll() {
        List<PayPeriod> periods = new ArrayList<>();
        payPeriodRepo.findAllByOrderByStartDateDesc().iterator().forEachRemaining(periods::add);
        return periods;
    }

    @Override
    public PayPeriod findById(long id) {
        return payPeriodRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format(
                        "Could not find PayPeriod: %s", id
                )));
    }

    @Override
    public PayPeriod save(PayPeriod payPeriod) {
        PayPeriod p = new PayPeriod();
        if (payPeriod.getId() != 0) {
            p.setId(payPeriod.getId());
        }
        p.setPeriod(payPeriod.getPeriodAsPeriod());
        p.setStartDate(payPeriod.getStartDate());
        p.setEndDate(payPeriod.getEndDate());
        return payPeriodRepo.save(p);
    }

    @Override
    public void delete(long id) {
        findById(id);
        payPeriodRepo.deleteById(id);
    }

    private boolean isPayPeriodCurrent(PayPeriod period) {
        Date today = Date.from(Instant.now());

        Period day = Period.ofDays(1);

        Date rangeStart = subtractDays(period.getStartDate(), day.getDays());
        Date rangeEnd = addDays(period.getEndDate(), day.getDays());

        return today.after(rangeStart) && today.before(rangeEnd);
    }

    private Date addDays(Date date, int amount) {
        Calendar d = Calendar.getInstance();
        d.setTime(date);
        d.add(Calendar.DAY_OF_MONTH, amount);
        return d.getTime();

//        return Date.from(
//                date
//                    .toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .plus(period)
//                    .toInstant()
//        );
    }

    private Date subtractDays(Date date, int amount) {

        Calendar d = Calendar.getInstance();
        d.setTime(date);
        d.add(Calendar.DAY_OF_MONTH, amount * -1);

        return d.getTime();

//        return Date.from(
//                date
//                    .toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .minus(period)
//                    .toInstant()
//        );
    }

    @Override
    public PayPeriod getCurrentPayPeriod() {

        List<PayPeriod> periods = findAll()
                .stream()
                .filter(p -> isPayPeriodCurrent(p))
                .collect(Collectors.toList());

//        System.out.println(String.format("periods before: %s", periods));

//        periods = periods.stream().filter(p -> isPayPeriodCurrent(p)).collect(Collectors.toList());

//        System.out.println(String.format("periods after: %s", periods));
        if (periods.size() != 1) {
            throw new ResourceFoundException("Error: Could not get current PayPeriod!");
        }
        return periods.getFirst();
    }

    @Override
    public PayPeriod getNextPayPeriod() {
        PayPeriod current = getCurrentPayPeriod();

        Date nextPeriodStartDate = addDays(current.getStartDate(), current.getPeriodAsPeriod().getDays());
        Optional<PayPeriod> next = payPeriodRepo.findByStartDate(nextPeriodStartDate);

        if (next.isEmpty()) {
            PayPeriod newPeriod = new PayPeriod();
            newPeriod.setPeriod(current.getPeriodAsPeriod());
            newPeriod.setStartDate(nextPeriodStartDate);
            newPeriod.setEndDate(addDays(current.getEndDate(), current.getPeriodAsPeriod().getDays()));

            newPeriod = save(newPeriod);
            return newPeriod;
        } else {
            return next.get();
        }
    }

    @Override
    public PayPeriod getPreviousPayPeriod() {
        PayPeriod current = getCurrentPayPeriod();
        Date prevStart = subtractDays(current.getStartDate(), current.getPeriodAsPeriod().getDays());

        return payPeriodRepo.findByStartDate(prevStart)
                .orElseThrow(() -> new DataNotFoundException(String.format(
                        "Could not find PayPeriod With startDate of: %s", prevStart
                )));
    }
}
