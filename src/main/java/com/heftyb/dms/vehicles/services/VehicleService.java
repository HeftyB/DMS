package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.vehicles.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> findAll();

    List<Vehicle> findByVin(String vin);

    Vehicle findByWholeVin(String vin);

    Vehicle findById(long id);

    Vehicle saveNew(Vehicle vehicle);

    Vehicle update(Vehicle vehicle);

    void delete(long id);

    Vehicle decodeVIN(String vin);

    Vehicle updateVehiclesCustomer(String vin, long id);
}
