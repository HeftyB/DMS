package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.account.services.PayPeriodService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.timekeeping.PTO;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimeSheet;
import com.heftyb.dms.timekeeping.repositories.TimeSheetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "timeSheetService")
public class TimeSheetServiceImp implements TimeSheetService {

    private final TimeSheetRepository tsRepo;
    private final TimeClockService timeClockService;
    private final PTOService ptoService;
    private final PayPeriodService payPeriodService;

    public TimeSheetServiceImp(final TimeSheetRepository tsRepo,
                               final TimeClockService timeClockService,
                               final PTOService ptoService,
                               final PayPeriodService payPeriodService) {
        this.tsRepo = tsRepo;
        this.timeClockService = timeClockService;
        this.ptoService = ptoService;
        this.payPeriodService = payPeriodService;
    }

    @Override
    public List<TimeSheet> findAllTimeSheets() {
        List<TimeSheet> timeSheets = new ArrayList<>();
        tsRepo.findAll().iterator().forEachRemaining(timeSheets::add);
        return timeSheets;
    }

    @Override
    public TimeSheet findTimeSheetById(long id) {
        return tsRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "TimeSheetService Error: can not find timeSheet id %g", id
                ))
        );
    }

    @Override
    public TimeSheet saveTimeSheet(TimeSheet timeSheet) {
        TimeSheet t = new TimeSheet();
        t.setPayPeriod(payPeriodService.findById(timeSheet.getPayPeriod().getId()));
        t.setTimeClockPunchSets(new ArrayList<>());

        for (TimeClockPunchSet tp : timeSheet.getTimeClockPunchSets()) {
            TimeClockPunchSet tt = timeClockService.findTimeClockPunchSetById(tp.getId());
            t.getTimeClockPunchSets().add(tt);
        }
        t.setPto(new ArrayList<>());
        for (PTO pto : timeSheet.getPto()) {
            PTO pp = ptoService.findById(pto.getId());
            t.getPto().add(pp);
        }

        t.setTotalHours(timeSheet.getTotalHours());
        return tsRepo.save(t);
    }

    @Override
    public void deleteTimeSheetById(long id) {
        findTimeSheetById(id);
        tsRepo.deleteById(id);
    }


}
