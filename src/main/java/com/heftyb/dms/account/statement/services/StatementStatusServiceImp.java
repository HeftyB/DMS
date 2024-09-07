package com.heftyb.dms.account.statement.services;

import com.heftyb.dms.account.services.PaymentTermService;
import com.heftyb.dms.account.statement.StatementStatusRate;
import com.heftyb.dms.account.statement.repositories.StatementStatusRateRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "statementStatusService")
public class StatementStatusServiceImp implements StatementStatusRateService {

    private final StatementStatusRateRepository statusRepo;
    private final PaymentTermService paymentTermService;

    public StatementStatusServiceImp(final StatementStatusRateRepository statusRepo,
                                     final PaymentTermService paymentTermService) {
        this.statusRepo = statusRepo;
        this.paymentTermService = paymentTermService;
    }

    @Override
    public List<StatementStatusRate> findAll() {
        List<StatementStatusRate> statusRates = new ArrayList<>();
        statusRepo.findAll().iterator().forEachRemaining(statusRates::add);
        return statusRates;
    }

    @Override
    public StatementStatusRate findById(long id) {
        return statusRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "StatementStatusService Error: can not find StatementStatus id %g", id
                ))
        );
    }

    @Override
    public StatementStatusRate save(StatementStatusRate statementStatusRate) {
        StatementStatusRate s = new StatementStatusRate();
        s.setStatus(statementStatusRate.getStatus());
        s.setRate(statementStatusRate.getRate());
        s.setPaymentTerm(paymentTermService.findById(statementStatusRate.getPaymentTerm().getId()));

        return statusRepo.save(s);
    }

    @Override
    public void delete(long id) {
        findById(id);
        statusRepo.deleteById(id);
    }
}
