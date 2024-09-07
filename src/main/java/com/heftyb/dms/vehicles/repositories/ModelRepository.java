package com.heftyb.dms.vehicles.repositories;

import com.heftyb.dms.vehicles.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends CrudRepository<Model, Long> {

    List<Model> findByManufacturer_NameContainingIgnoreCase(String name);

    Optional<Model> findByNameIgnoreCase(String name);

    List<Model> findByNameContainingIgnoreCase(String name);
}
