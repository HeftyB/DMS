package com.heftyb.dms.account.tax.services;

import com.heftyb.dms.account.tax.FederalTax;
import com.heftyb.dms.account.tax.LocalTax;
import com.heftyb.dms.account.tax.StateTax;
import com.heftyb.dms.account.tax.repositories.FederalTaxRepository;
import com.heftyb.dms.account.tax.repositories.LocalTaxRepository;
import com.heftyb.dms.account.tax.repositories.StateTaxRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "taxService")
public class TaxServiceImp implements TaxService {

    private final FederalTaxRepository fedRepo;
    private final StateTaxRepository stateRepo;
    private final LocalTaxRepository localRepo;
    ;

    public TaxServiceImp(final FederalTaxRepository fedRepo,
                         final StateTaxRepository stateRepo,
                         final LocalTaxRepository localRepo) {
        this.fedRepo = fedRepo;
        this.stateRepo = stateRepo;
        this.localRepo = localRepo;
    }

    @Override
    public List<FederalTax> findAllFederalTax() {
        List<FederalTax> federalTaxes = new ArrayList<>();
        fedRepo.findAll().iterator().forEachRemaining(federalTaxes::add);
        return federalTaxes;
    }

    @Override
    public List<StateTax> findAllStateTax() {
        List<StateTax> stateTaxes = new ArrayList<>();
        stateRepo.findAll().iterator().forEachRemaining(stateTaxes::add);
        return stateTaxes;
    }

    @Override
    public List<LocalTax> findAllLocalTax() {
        List<LocalTax> localTaxes = new ArrayList<>();
        localRepo.findAll().iterator().forEachRemaining(localTaxes::add);
        return localTaxes;
    }

    @Override
    public FederalTax findFederalTaxById(long id) {
        return fedRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "TaxService Error: could not find FederalTax id %g", id
                ))
        );
    }

    @Override
    public StateTax findStateTaxById(long id) {
        return stateRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "TaxService Error: could not find StateTax id %g", id
                ))
        );
    }

    @Override
    public LocalTax findLocalTaxById(long id) {
        return localRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "TaxService Error: could not find LocalTax id %g", id
                ))
        );
    }

    @Override
    public FederalTax saveNewFederalTax(FederalTax federalTax) {
        FederalTax f = new FederalTax();
        f.setActive(federalTax.isActive());
        f.setRate(federalTax.getRate());
        f.setType(federalTax.getType());
        f.setTaxCharges(new ArrayList<>());

        return fedRepo.save(f);
    }

    @Override
    public StateTax saveNewStateTax(StateTax stateTax) {
        StateTax s = new StateTax();
        s.setActive(stateTax.isActive());
        s.setType(stateTax.getType());
        s.setRate(stateTax.getRate());
        s.setTaxCharge(new ArrayList<>());

        return stateRepo.save(s);
    }

    @Override
    public LocalTax saveNewLocalTax(LocalTax localTax) {
        LocalTax l = new LocalTax();
        l.setActive(localTax.isActive());
        l.setType(localTax.getType());
        l.setRate(localTax.getRate());
        l.setTaxCharge(new ArrayList<>());

        return null;
    }

    @Override
    public void deleteFederalTax(long id) {
        findFederalTaxById(id);
        fedRepo.deleteById(id);
    }

    @Override
    public void deleteStateTax(long id) {
        findStateTaxById(id);
        stateRepo.deleteById(id);
    }

    @Override
    public void deleteLocalTax(long id) {
        findLocalTaxById(id);
        localRepo.deleteById(id);
    }
}
