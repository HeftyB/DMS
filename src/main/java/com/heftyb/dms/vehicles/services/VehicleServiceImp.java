package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.vehicles.Manufacturer;
import com.heftyb.dms.vehicles.Model;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.repositories.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service(value = "vehicleService")
public class VehicleServiceImp implements VehicleService {

    private final VehicleRepository vehicleRepo;
    private final ManufacturerService manService;
    private final CustomerService customerService;

    public VehicleServiceImp(
            final VehicleRepository vehicleRepo,
            final ManufacturerService manufacturerService,
            final CustomerService customerService
    ) {
        this.vehicleRepo = vehicleRepo;
        manService = manufacturerService;
        this.customerService = customerService;
    }

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicleRepo.findAll().iterator().forEachRemaining(vehicles::add);
        return vehicles;
    }

    @Override
    public List<Vehicle> findByVin(String vin) {
        return vehicleRepo.findByVinContainingIgnoreCase(vin).stream().collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Vehicle findById(long id) {
        return vehicleRepo.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("VehicleService Error: could not find vehicle id %g", id)));
    }

    @Override
    public Vehicle saveNew(Vehicle vehicle) {
        Vehicle v = new Vehicle();

        v.setVin(vehicle.getVin());
        v.setModelYear(vehicle.getModelYear());

        Manufacturer manufacturer = manService.findById(vehicle.getMake().getId());

        v.setMake(manufacturer);
        v.setModel(vehicle.getModel());


        v.setTrim(vehicle.getTrim());
        v.setEngine(vehicle.getEngine());
        v.setColor(vehicle.getColor());

        Customer customer = customerService.findById(vehicle.getCustomer().getId());

        v.setCustomer(customer);

        return vehicleRepo.save(v);
    }
    
    @Override
    public Vehicle update(Vehicle vehicle) {
        Vehicle v = findById(vehicle.getId());

        if (vehicle.getVin() != null) v.setVin(vehicle.getVin());
        if (vehicle.getModel() != null) v.setModel(vehicle.getModel());
        if (vehicle.getTrim() != null) v.setTrim(vehicle.getTrim());
        if (vehicle.getEngine() != null)v.setEngine(vehicle.getEngine());
        if (vehicle.getColor() != null) v.setColor(vehicle.getColor());
        if (vehicle.getCustomer() != null) v.setCustomer(customerService.findById(vehicle.getCustomer().getId()));

        return vehicleRepo.save(v);
    }
    
    @Override
    public void delete(long id) {
        findById(id);
        vehicleRepo.deleteById(id);
    }
}
