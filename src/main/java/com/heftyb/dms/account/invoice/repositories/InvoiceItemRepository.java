package com.heftyb.dms.account.invoice.repositories;

import com.heftyb.dms.account.invoice.InvoiceItem;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceItemRepository extends CrudRepository<InvoiceItem, Long> {
}
