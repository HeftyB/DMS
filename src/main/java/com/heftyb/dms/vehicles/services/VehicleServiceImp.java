package com.heftyb.dms.vehicles.services;

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
public class VehicleServiceImp implements VehicleService{

    private final VehicleRepository vehicleRepo;
    private final ManufacturerService manService;
    private final ModelService modelService;

    public VehicleServiceImp(
            final VehicleRepository vehicleRepo,
            final ManufacturerService manufacturerService,
            final ModelService modelService
    ) {
        this.vehicleRepo = vehicleRepo;
        manService = manufacturerService;
        this.modelService = modelService;
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
        return vehicleRepo.findById(id).orElseThrow(()-> new DataNotFoundException(String.format("VehicleService Error: could not find vehicle id %g", id)));
    }

    @Transactional
    @Override
    public Vehicle save(Vehicle vehicle) {
        Vehicle v = new Vehicle();

        v.setVin(vehicle.getVin());
        v.setModelYear(vehicle.getModelYear());

        Manufacturer manufacturer = manService.findById(vehicle.getMake().getId());

        v.setMake(manufacturer);

        Model model = modelService.findById(vehicle.getModel().getId());

        v.setModel(model);

        v.setTrim(vehicle.getTrim());
        v.setEngine(vehicle.getEngine());
        v.setColor(vehicle.getColor());

//        set ros & customer
        return null;
    }

    @Transactional
    @Override
    public void delete(long id) {
        findById(id);
        vehicleRepo.deleteById(id);
    }
}
