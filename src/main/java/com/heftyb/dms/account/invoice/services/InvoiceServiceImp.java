package com.heftyb.dms.account.invoice.services;

import com.heftyb.dms.account.invoice.Invoice;
import com.heftyb.dms.account.invoice.InvoiceItem;
import com.heftyb.dms.account.invoice.repositories.InvoiceRepository;
import com.heftyb.dms.account.services.PaymentTermService;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.crm.services.VendorService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "invoiceService")
public class InvoiceServiceImp implements InvoiceService{

    private final InvoiceRepository invoiceRepo;
    private final CustomerService customerService;
    private final VendorService vendorService;
    private final PaymentTermService termService;
    private final InvoiceItemService itemService;

    public InvoiceServiceImp(final InvoiceRepository invoiceRepo,
                             final CustomerService customerService,
                             final VendorService vendorService,
                             final PaymentTermService termService,
                             final InvoiceItemService itemService) {
        this.invoiceRepo = invoiceRepo;
        this.customerService = customerService;
        this.vendorService = vendorService;
        this.termService = termService;
        this.itemService = itemService;
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        invoiceRepo.findAll().iterator().forEachRemaining(invoices::add);
        return invoices;
    }

    @Override
    public Invoice findById(long id) {
        return invoiceRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "InvoiceService Error: can not find Invoice id %g", id
                ))
        );
    }

    @Override
    public Invoice save(Invoice invoice) {
        Invoice i = new Invoice();
        i.setType(invoice.getType());
        i.setInvoiceNumber(invoice.getInvoiceNumber());
        i.setPoNumber(invoice.getPoNumber());
        i.setDate(invoice.getDate());
        i.setTotal(invoice.getTotal());
        i.setCustomer(customerService.findById(invoice.getCustomer().getId()));
        i.setVendor(vendorService.findById(invoice.getVendor().getId()));
        i.setTerms(termService.findById(invoice.getTerms().getId()));
        i.setNotes(invoice.getNotes());
        i.setStatus(invoice.getStatus());
        i.setItems(new ArrayList<>());

        for (InvoiceItem item : invoice.getItems()) {
            item.setInvoice(i);
            InvoiceItem ii = itemService.save(item);
            i.getItems().add(ii);
        }

        return invoiceRepo.save(i);
    }

    @Override
    public void delete(long id) {
        findById(id);
        invoiceRepo.deleteById(id);
    }
}
