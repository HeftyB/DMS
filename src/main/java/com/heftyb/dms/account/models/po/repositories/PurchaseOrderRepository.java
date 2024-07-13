package com.heftyb.dms.account.models.po.repositories;

import com.heftyb.dms.account.models.po.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {
}
