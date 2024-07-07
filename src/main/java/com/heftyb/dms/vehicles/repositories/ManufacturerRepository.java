package com.heftyb.dms.vehicles.repositories;

import com.heftyb.dms.vehicles.Manufacturer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByName(String name);
    List<Manufacturer> findByNameContainingIgnoreCase(String name);
}

