package com.heftyb.dms.account.services;

import com.heftyb.dms.account.PayPeriod;

import java.util.List;

public interface PayPeriodService {
    List<PayPeriod> findAll();

    PayPeriod findById(long id);

    PayPeriod save(PayPeriod payPeriod);

    void delete(long id);

    PayPeriod getCurrentPayPeriod();

    PayPeriod getNextPayPeriod();

    PayPeriod getPreviousPayPeriod();
}
