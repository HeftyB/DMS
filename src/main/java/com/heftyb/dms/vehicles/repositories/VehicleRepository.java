package com.heftyb.dms.vehicles.repositories;

import com.heftyb.dms.vehicles.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    List<Vehicle> findByVinContainingIgnoreCase(String vin);
    Optional<Vehicle> findByVin(String vin);
}
