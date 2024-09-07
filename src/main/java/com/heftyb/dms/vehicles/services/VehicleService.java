package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.vehicles.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    List<Vehicle> findByVin(String vin);

    Vehicle findById(long id);

    Vehicle save(Vehicle vehicle);

    void delete(long id);
}
