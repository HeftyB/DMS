package com.heftyb.dms.account.invoice.services;

import com.heftyb.dms.account.invoice.InvoiceItem;

import java.util.List;

public interface InvoiceItemService {
    List<InvoiceItem> findAll();
    InvoiceItem findById(long id);
    InvoiceItem save(InvoiceItem invoiceItem);
    void delete(long id);
}
