package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.MailingAddress;
import com.heftyb.dms.crm.Vendor;
import com.heftyb.dms.crm.repositories.MailingAddressRepository;
import com.heftyb.dms.crm.repositories.VendorRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "vendorService")
public class VendorServiceImp implements VendorService{

    private final VendorRepository vendorRepository;
    private final MailingAddressRepository mailRepo;

    public VendorServiceImp(final VendorRepository vendorRepository,
                            final MailingAddressRepository mailRepo) {
        this.vendorRepository = vendorRepository;
        this.mailRepo = mailRepo;
    }

    @Override
    public List<Vendor> findAll() {
        List<Vendor> vendors = new ArrayList<>();
        vendorRepository.findAll().iterator().forEachRemaining(vendors::add);
        return vendors;
    }

    @Override
    public List<Vendor> findByName(String name) {
        List<Vendor> vendors = new ArrayList<>();
        vendorRepository.findByCompanyNameContainsIgnoreCase(name).iterator().forEachRemaining(vendors::add);
        return vendors;
    }

    @Override
    public Vendor findById(long id) {
        return vendorRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "VendorService Error: can not find vendor id %g", id
                ))
        );
    }

    @Override
    public Vendor save(Vendor vendor) {
        Vendor v = new Vendor();
        v.setCompanyName(vendor.getCompanyName());
        v.setPersonOfContact(vendor.getPersonOfContact());
        vendor.getAddress().setVendor(v);

        MailingAddress m = new MailingAddress();
        m.setName(vendor.getAddress().getName());
        m.setAddressLine1(vendor.getAddress().getAddressLine1());
        m.setAddressLine2(vendor.getAddress().getAddressLine2());
        m.setCity(vendor.getAddress().getCity());
        m.setState(vendor.getAddress().getState());
        m.setZip(vendor.getAddress().getZip());

        m.setVendor(v);

        v.setAddress(mailRepo.save(m));

        v.setPhone(vendor.getPhone());
        v.setEmail(vendor.getEmail());
        v.setTaxId(vendor.getTaxId());
        v.setPaymentMethod(vendor.getPaymentMethod());
        v.setSentPurchaseOrders(new ArrayList<>());
        v.setReceivedPurchaseOrders(new ArrayList<>());
        v.setInvoices(new ArrayList<>());

        return vendorRepository.save(v);
    }

    @Override
    public void delete(long id) {
        findById(id);
        vendorRepository.deleteById(id);
    }
}

