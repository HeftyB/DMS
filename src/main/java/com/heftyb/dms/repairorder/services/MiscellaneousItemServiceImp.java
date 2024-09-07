package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.account.po.services.PurchaseOrderService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.MiscellaneousItem;
import com.heftyb.dms.repairorder.repositories.MiscellaneousItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "miscellaneousService")
public class MiscellaneousItemServiceImp implements MiscellaneousItemsService {

    final private MiscellaneousItemRepository miscRepo;
    final private RepairOrderService repairOrderService;
    final private PurchaseOrderService purchaseOrderService;

    public MiscellaneousItemServiceImp(final MiscellaneousItemRepository miscRepo,
                                       final RepairOrderService repairOrderService,
                                       final PurchaseOrderService purchaseOrderService) {
        this.miscRepo = miscRepo;
        this.repairOrderService = repairOrderService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public List<MiscellaneousItem> findAll() {
        List<MiscellaneousItem> items = new ArrayList<>();
        miscRepo.findAll().iterator().forEachRemaining(items::add);
        return items;
    }

    @Override
    public MiscellaneousItem findById(long id) {
        return miscRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "MiscellaneousItemsService Error: could not find miscellaneousItem id %g", id
                ))
        );
    }

    @Override
    public MiscellaneousItem save(MiscellaneousItem item) {
        MiscellaneousItem m = new MiscellaneousItem();
        m.setDescription(item.getDescription());
        m.setCost(item.getCost());
        m.setRepairOrder(repairOrderService.findById(item.getRepairOrder().getId()));
        m.setPurchaseOrder(purchaseOrderService.findPurchaseOrderById(item.getPurchaseOrder().getId()));
        return miscRepo.save(m);
    }

    @Override
    public void delete(long id) {
        findById(id);
        miscRepo.deleteById(id);
    }
}
