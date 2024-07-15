package com.heftyb.dms.account.po.repositories;

import com.heftyb.dms.account.po.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {
}
