package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Vendor;

import java.util.List;

public interface VendorService {
    List<Vendor> findAll();

    List<Vendor> findByName(String name);

    Vendor findById(long id);

    Vendor saveNew(Vendor vendor);

    Vendor update(Vendor vendor);

    void delete(long id);
}
