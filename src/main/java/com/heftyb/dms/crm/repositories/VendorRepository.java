package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.Vendor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VendorRepository extends CrudRepository<Vendor, Long> {
    List<Vendor> findByCompanyNameContainsIgnoreCase(String name);
}
