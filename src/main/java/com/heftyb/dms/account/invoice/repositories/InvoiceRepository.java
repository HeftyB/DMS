package com.heftyb.dms.account.invoice.repositories;

import com.heftyb.dms.account.invoice.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
