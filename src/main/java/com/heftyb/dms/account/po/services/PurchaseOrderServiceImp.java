package com.heftyb.dms.account.po.services;

import com.heftyb.dms.account.po.POItem;
import com.heftyb.dms.account.po.PurchaseOrder;
import com.heftyb.dms.account.po.repositories.POItemRepository;
import com.heftyb.dms.account.po.repositories.PurchaseOrderRepository;
import com.heftyb.dms.crm.services.ContactService;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.crm.services.VendorService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "purchaseOrderService")
public class PurchaseOrderServiceImp implements PurchaseOrderService{

    private final PurchaseOrderRepository poRepo;
    private final POItemRepository itemRepo;
    private final VendorService vendorService;
    private final ContactService contactService;
    private final EmployeeService employeeService;

    public PurchaseOrderServiceImp(final PurchaseOrderRepository poRepo,
                                   final POItemRepository itemRepo,
                                   final VendorService vendorService,
                                   final ContactService contactService,
                                   final EmployeeService employeeService) {
        this.poRepo = poRepo;
        this.itemRepo = itemRepo;
        this.vendorService = vendorService;
        this.contactService = contactService;
        this.employeeService = employeeService;
    }

    @Override
    public List<PurchaseOrder> findAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        poRepo.findAll().iterator().forEachRemaining(purchaseOrders::add);
        return purchaseOrders;
    }

    @Override
    public PurchaseOrder findPurchaseOrderById(long id) {
        return poRepo.findById(id).orElseThrow(() -> new DataNotFoundException(String.format(
                "PurchaseOrder Service Error: could not find po id %g", id
        )));
    }

    @Override
    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrder p = new PurchaseOrder();
        p.setDate(purchaseOrder.getDate());
        p.setFrom(vendorService.findById(purchaseOrder.getFrom().getId()));
        p.setTo(vendorService.findById(purchaseOrder.getFrom().getId()));
        p.setFromContact(contactService.findContactInformationById(purchaseOrder.getFromContact().getId()));
        p.setToContact(contactService.findContactInformationById(purchaseOrder.getFromContact().getId()));
        p.setShippingMethod(purchaseOrder.getShippingMethod());
        p.setPaymentTerms(purchaseOrder.getPaymentTerms());
        p.setRequiredByDate(purchaseOrder.getRequiredByDate());
        p.setNotes(purchaseOrder.getNotes());

        p.setItems(new ArrayList<>());
        for (POItem poItem : purchaseOrder.getItems()) {
            poItem.setPurchaseOrder(p);
            POItem poI = savePoItem(poItem);
            p.getItems().add(poI);
        }

        p.setSubTotal(purchaseOrder.getSubTotal());

        p.setShipping(purchaseOrder.getShipping());
        p.setOther(purchaseOrder.getOther());
        p.setTotalCost(purchaseOrder.getTotalCost());

        p.setApprovedBy(employeeService.findById(purchaseOrder.getApprovedBy().getId()));

        return poRepo.save(p);
    }

    @Override
    public void deletePurchaseOrder(long id) {
        findPurchaseOrderById(id);
        poRepo.deleteById(id);
    }

    @Override
    public List<POItem> findAllPoItems() {
        List<POItem> poItems = new ArrayList<>();
        itemRepo.findAll().iterator().forEachRemaining(poItems::add);
        return poItems;
    }

    @Override
    public POItem findPoItemById(long id) {
        return itemRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "PoItemService Error: could not find POItem id %g", id
                ))
        );
    }

    @Override
    public POItem savePoItem(POItem poItem) {
        POItem p = new POItem();
        p.setQty(poItem.getQty());
        p.setTotalCost(poItem.getTotalCost());
        p.setDescription(p.getDescription());
        p.setUnit1(poItem.getUnit1());
        p.setUnit2(poItem.getUnit2());
        p.setPurchaseOrder(findPurchaseOrderById(poItem.getPurchaseOrder().getId()));

        return itemRepo.save(p);
    }

    @Override
    public void deletePoItem(long id) {
        findPoItemById(id);
        itemRepo.deleteById(id);
    }
}
