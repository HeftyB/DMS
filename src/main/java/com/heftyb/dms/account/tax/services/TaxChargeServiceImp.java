package com.heftyb.dms.account.tax.services;

import com.heftyb.dms.account.po.services.PurchaseOrderService;
import com.heftyb.dms.account.tax.TaxCharge;
import com.heftyb.dms.account.tax.repositories.TaxChargeRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "taxChargeService")
public class TaxChargeServiceImp implements TaxChargeService {

    private final TaxChargeRepository taxChargeRepo;
    private final TaxService taxService;
    private final RepairOrderService repairOrderService;
    private final PurchaseOrderService purchaseOrderService;

    public TaxChargeServiceImp(final TaxChargeRepository taxChargeRepo,
                               final TaxService taxService,
                               final RepairOrderService repairOrderService,
                               final PurchaseOrderService purchaseOrderService) {
        this.taxChargeRepo = taxChargeRepo;
        this.taxService = taxService;
        this.repairOrderService = repairOrderService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public List<TaxCharge> findAll() {
        List<TaxCharge> taxCharges = new ArrayList<>();
        taxChargeRepo.findAll().iterator().forEachRemaining(taxCharges::add);
        return taxCharges;
    }

    @Override
    public TaxCharge findById(long id) {
        return taxChargeRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "TaxChargeService Error: could not find taxCharge id %g", id
                ))
        );
    }

    @Override
    public TaxCharge save(TaxCharge taxCharge) {
        TaxCharge tc = new TaxCharge();
        tc.setType(taxCharge.getType());
        tc.setRepairOrder(repairOrderService.findById(taxCharge.getRepairOrder().getId()));
        tc.setPurchaseOrder(purchaseOrderService.findPurchaseOrderById(taxCharge.getPurchaseOrder().getId()));
        tc.setFederal(taxService.findFederalTaxById(taxCharge.getFederal().getId()));
        tc.setState(taxService.findStateTaxById(taxCharge.getState().getId()));
        tc.setLocal(taxService.findLocalTaxById(taxCharge.getLocal().getId()));
        tc.setTotalTax(taxCharge.getTotalTax());

        return taxChargeRepo.save(tc);
    }

    @Override
    public void delete(long id) {
        findById(id);
        taxChargeRepo.deleteById(id);
    }
}
