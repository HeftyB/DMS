package com.heftyb.dms.account.po.services;

import com.heftyb.dms.account.po.POItem;
import com.heftyb.dms.account.po.repositories.POItemRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "poItemService")
public class PoItemServiceImp implements PoItemService{

    final private POItemRepository poItemRepo;
    final private PurchaseOrderService purchaseOrderService;

    public PoItemServiceImp (final POItemRepository poItemRepo,
                             final PurchaseOrderService purchaseOrderService) {
        this.poItemRepo = poItemRepo;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public List<POItem> findAll() {
        List<POItem> poItems = new ArrayList<>();
        poItemRepo.findAll().iterator().forEachRemaining(poItems::add);
        return poItems;
    }

    @Override
    public POItem findById(long id) {
        return poItemRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "PoItemService Error: could not find POItem id %g", id
                ))
        );
    }

    @Override
    public POItem save(POItem poItem) {
        POItem p = new POItem();
        p.setQty(poItem.getQty());
        p.setTotalCost(poItem.getTotalCost());
        p.setDescription(p.getDescription());
        p.setUnit1(poItem.getUnit1());
        p.setUnit2(poItem.getUnit2());
        p.setPurchaseOrder(purchaseOrderService.findPurchaseOrderById(poItem.getPurchaseOrder().getId()));

        return poItemRepo.save(p);
    }

    @Override
    public void delete(long id) {
        findById(id);
        poItemRepo.deleteById(id);
    }
}
