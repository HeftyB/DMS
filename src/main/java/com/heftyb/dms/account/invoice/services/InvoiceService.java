package com.heftyb.dms.account.invoice.services;

import com.heftyb.dms.account.invoice.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> findAll();

    Invoice findById(long id);

    Invoice save(Invoice invoice);

    void delete(long id);
}
