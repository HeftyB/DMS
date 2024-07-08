package com.heftyb.dms.vehicles.repositories;

import com.heftyb.dms.vehicles.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    List<Vehicle> findByVinContaining(String vin);
}
