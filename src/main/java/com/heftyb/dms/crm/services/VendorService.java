package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Vendor;

import java.util.List;

public interface VendorService {
    List<Vendor> findAll();
    List<Vendor> findByName(String name);
    Vendor findById(long id);
    Vendor save(Vendor vendor);
    void delete(long id);
}
