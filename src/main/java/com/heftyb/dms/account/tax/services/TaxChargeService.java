package com.heftyb.dms.account.tax.services;

import com.heftyb.dms.account.tax.TaxCharge;

import java.util.List;

public interface TaxChargeService {
    List<TaxCharge> findAll();
    TaxCharge findById(long id);
    TaxCharge save(TaxCharge taxCharge);
    void delete(long id);
}
