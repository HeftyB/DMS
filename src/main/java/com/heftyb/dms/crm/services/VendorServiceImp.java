package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Vendor;
import com.heftyb.dms.crm.repositories.VendorRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "vendorService")
public class VendorServiceImp implements VendorService {

    private final VendorRepository vendorRepository;

    public VendorServiceImp(final VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
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

    @Override
    public Vendor saveNew(Vendor vendor) {
        Vendor v = new Vendor();
        v.setCompanyName(vendor.getCompanyName());
        v.setAddress(vendor.getAddress());
        v.setContactInformation(vendor.getContactInformation());
        v.setEmail(vendor.getEmail());
        v.setTaxId(vendor.getTaxId());
        v.setPaymentMethod(vendor.getPaymentMethod());

        return vendorRepository.save(v);
    }

    @Override
    public Vendor update(Vendor vendor) {
        Vendor v = findById(vendor.getId());

        if (vendor.getCompanyName() != null) v.setCompanyName(vendor.getCompanyName());
        if (vendor.getAddress() != null) v.setAddress(vendor.getAddress());
        if (vendor.getContactInformation() != null) v.setContactInformation(vendor.getContactInformation());
        if (vendor.getEmail() != null) v.setEmail(vendor.getEmail());
        if (vendor.getTaxId() != null) v.setTaxId(vendor.getTaxId());
        if (vendor.getPaymentMethod() != null) v.setPaymentMethod(vendor.getPaymentMethod());

        return vendorRepository.save(v);
    }

    @Override
    public void delete(long id) {
        findById(id);
        vendorRepository.deleteById(id);
    }
}

