package com.heftyb.dms.account.tax.services;

import com.heftyb.dms.account.tax.FederalTax;
import com.heftyb.dms.account.tax.LocalTax;
import com.heftyb.dms.account.tax.StateTax;

import java.util.List;

public interface TaxService {
    List<FederalTax> findAllFederalTax();

    List<StateTax> findAllStateTax();

    List<LocalTax> findAllLocalTax();

    FederalTax findFederalTaxById(long id);

    StateTax findStateTaxById(long id);

    LocalTax findLocalTaxById(long id);

    FederalTax saveNewFederalTax(FederalTax federalTax);

    StateTax saveNewStateTax(StateTax stateTax);

    LocalTax saveNewLocalTax(LocalTax localTax);

    void deleteFederalTax(long id);

    void deleteStateTax(long id);

    void deleteLocalTax(long id);
}
