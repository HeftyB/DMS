package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.PhoneNumber;
import com.heftyb.dms.crm.Vendor;
import com.heftyb.dms.crm.repositories.VendorRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Transactional
@Service(value = "vendorService")
public class VendorServiceImp implements VendorService {

    private final VendorRepository vendorRepository;
    private final ContactService contactService;

    public VendorServiceImp(final VendorRepository vendorRepository,
                            final ContactService contactService) {
        this.vendorRepository = vendorRepository;
        this.contactService = contactService;
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
                () -> new DataNotFoundException(String.format(
                        "VendorService Error: can not find vendor id %g", id
                ))
        );
    }

    @Transactional
    @Override
    public Vendor save(Vendor vendor) {
        Vendor v = new Vendor();
        v.setCompanyName(vendor.getCompanyName());
        v.setPersonOfContact(vendor.getPersonOfContact());
        vendor.getAddress().setVendor(v);

        v = vendorRepository.save(v);

        v.setAddress(contactService.saveNewMailingAddress(vendor.getAddress()));

        for (PhoneNumber pn : v.getPhoneNumbers()) {
            pn.setVendor(v);
            v.getPhoneNumbers().add(contactService.saveNewPhoneNumber(pn));
        }

        v.setEmail(vendor.getEmail());
        v.setTaxId(vendor.getTaxId());
        v.setPaymentMethod(vendor.getPaymentMethod());

        return vendorRepository.save(v);
    }

    @Transactional
    @Override
    public void delete(long id) {
        findById(id);
        vendorRepository.deleteById(id);
    }
}

