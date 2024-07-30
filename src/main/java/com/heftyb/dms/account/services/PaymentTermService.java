package com.heftyb.dms.account.services;

import com.heftyb.dms.account.PaymentTerm;

import java.util.List;

public interface PaymentTermService {
    List<PaymentTerm> findAll();
    PaymentTerm findById(long id);
    PaymentTerm save(PaymentTerm paymentTerm);
    void delete(long id);
}
