package com.heftyb.dms.account.po.services;

import com.heftyb.dms.account.po.POItem;
import com.heftyb.dms.account.po.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {
    List<PurchaseOrder> findAllPurchaseOrders();

    PurchaseOrder findPurchaseOrderById(long id);

    PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);

    void deletePurchaseOrder(long id);

    List<POItem> findAllPoItems();

    POItem findPoItemById(long id);

    POItem savePoItem(POItem poItem);

    void deletePoItem(long id);
}
