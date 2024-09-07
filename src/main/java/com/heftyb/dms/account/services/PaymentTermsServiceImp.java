package com.heftyb.dms.account.services;

import com.heftyb.dms.account.PaymentTerm;
import com.heftyb.dms.account.repositories.PaymentTermRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "paymentTermsService")
public class PaymentTermsServiceImp implements PaymentTermService {

    final private PaymentTermRepository termRepo;

    public PaymentTermsServiceImp(final PaymentTermRepository termRepo) {
        this.termRepo = termRepo;
    }

    @Override
    public List<PaymentTerm> findAll() {
        List<PaymentTerm> paymentTerms = new ArrayList<>();
        termRepo.findAll().iterator().forEachRemaining(paymentTerms::add);
        return paymentTerms;
    }

    @Override
    public PaymentTerm findById(long id) {
        return termRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "PaymentTermService Error: can not find paymentTerm id %g", id
                ))
        );
    }

    @Override
    public PaymentTerm save(PaymentTerm paymentTerm) {
        PaymentTerm p = new PaymentTerm();
        p.setTerms(paymentTerm.getTerms());
        p.setStatusRates(new ArrayList<>());

        return termRepo.save(p);
    }

    @Override
    public void delete(long id) {
        findById(id);
        termRepo.deleteById(id);
    }
}
