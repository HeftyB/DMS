package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.vehicles.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    List<Manufacturer> findAll();
    Manufacturer findById(long id);
    Manufacturer findByName(String name);
    List<Manufacturer> findByNameContaining(String name);

    void delete(long id);
    Manufacturer save (Manufacturer manufacturer);
    Manufacturer update (long id, Manufacturer manufacturer);
}
