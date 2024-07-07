package com.heftyb.dms.vehicles.repositories;

import com.heftyb.dms.vehicles.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends CrudRepository<Model, Long> {

    Optional<Model> findByManufacturer_Name(String name);
    Optional<Model> findByName(String name);
    List<Model> findByNameContaining(String name);
}
