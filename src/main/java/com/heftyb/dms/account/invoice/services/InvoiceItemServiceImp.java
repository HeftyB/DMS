package com.heftyb.dms.account.invoice.services;

import com.heftyb.dms.account.invoice.InvoiceItem;
import com.heftyb.dms.account.invoice.repositories.InvoiceItemRepository;
import com.heftyb.dms.account.invoice.repositories.InvoiceRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "invoiceItemService")
public class InvoiceItemServiceImp implements InvoiceItemService{

    final private InvoiceItemRepository itemRepo;
    final private InvoiceRepository invoiceRepo;

    public InvoiceItemServiceImp(final InvoiceItemRepository itemRepo,
                                 final InvoiceRepository invoiceRepo) {
        this.itemRepo = itemRepo;
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    public List<InvoiceItem> findAll() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        itemRepo.findAll().iterator().forEachRemaining(invoiceItems::add);
        return invoiceItems;
    }

    @Override
    public InvoiceItem findById(long id) {
        return itemRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "InvoiceItemService Error: can not find InvoiceItem id %g", id
                ))
        );
    }

    @Override
    public InvoiceItem save(InvoiceItem invoiceItem) {
        InvoiceItem i = new InvoiceItem();
        i.setDescription(invoiceItem.getDescription());
        i.setQuantity(invoiceItem.getQuantity());
        i.setRate(invoiceItem.getRate());
        i.setTotal(invoiceItem.getTotal());
        i.setInvoice(invoiceRepo.findById(invoiceItem.getInvoice().getId())
        .orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "Could not find invoice id %g", invoiceItem.getInvoice().getId()
                ))
        ));
        return itemRepo.save(i);
    }

    @Override
    public void delete(long id) {
        findById(id);
        itemRepo.deleteById(id);
    }
}
