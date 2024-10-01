package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.dao.VinDecoderResponse;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.repositories.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service(value = "vehicleService")
public class VehicleServiceImp implements VehicleService {

    private final VehicleRepository vehicleRepo;
    private final CustomerService customerService;

    public VehicleServiceImp(final VehicleRepository vehicleRepo,
                             final CustomerService customerService) {
        this.vehicleRepo = vehicleRepo;
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
        return new ArrayList<>(vehicleRepo.findByVinContainingIgnoreCase(vin));
    }

    @Override
    public Vehicle findByWholeVin(String vin) {
        return vehicleRepo.findByVin(vin).orElseThrow(() -> new DataNotFoundException(
                String.format("Could not find vin %s", vin)
        ));
    }

    @Override
    public Vehicle findById(long id) {
        return vehicleRepo.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("VehicleService Error: could not find vehicle id %s", id)));
    }

    @Override
    public Vehicle saveNew(Vehicle vehicle) {
        Vehicle v = new Vehicle();

        v.setVin(vehicle.getVin());
        v.setModelYear(vehicle.getModelYear());
        v.setModel(vehicle.getModel());
        v.setMake(vehicle.getMake());
        v.setManufacturer(vehicle.getManufacturer());
        v.setPlantCompanyName(vehicle.getPlantCompanyName());
        v.setPlantCity(vehicle.getPlantCity());
        v.setPlantState(vehicle.getPlantState());
        v.setTransmissionStyle(vehicle.getTransmissionStyle());
        v.setDriveType(vehicle.getDriveType());
        v.setEngineModel(vehicle.getEngineModel());
        v.setEngineManufacturer(vehicle.getEngineManufacturer());
        v.setEngineType(vehicle.getEngineType());
        v.setDisplacementL(vehicle.getDisplacementL());
        v.setFuelType(vehicle.getFuelType());
        v.setBodyClass(vehicle.getBodyClass());
        v.setBasePrice(vehicle.getBasePrice());
        v.setDoors(vehicle.getDoors());
        v.setTrim(vehicle.getTrim());
        v.setTrim2(vehicle.getTrim2());
        v.setVehicleType(vehicle.getVehicleType());
        v.setColor(vehicle.getColor());

        if (vehicle.getCustomer() != null) {

            Customer customer = customerService.findById(vehicle.getCustomer().getId());

            v.setCustomer(customer);
        }
        return vehicleRepo.save(v);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        Vehicle v = findById(vehicle.getId());

        if (vehicle.getVin() != null) v.setVin(vehicle.getVin());
        if (vehicle.getModel() != null) v.setModelYear(vehicle.getModelYear());
        if (vehicle.getMake() != null) v.setMake(vehicle.getMake());
        if (vehicle.getManufacturer() != null) v.setManufacturer(vehicle.getManufacturer());
        if (vehicle.getPlantCompanyName() != null) v.setPlantCompanyName(vehicle.getPlantCompanyName());
        if (vehicle.getPlantCity() != null) v.setPlantCity(vehicle.getPlantCity());
        if (vehicle.getPlantState() != null) v.setPlantState(vehicle.getPlantState());
        if (vehicle.getTransmissionStyle() != null) v.setTransmissionStyle(vehicle.getTransmissionStyle());
        if (vehicle.getDriveType() != null) v.setDriveType(vehicle.getDriveType());
        if (vehicle.getEngineModel() != null) v.setEngineModel(vehicle.getEngineModel());
        if (vehicle.getEngineManufacturer() != null) v.setEngineManufacturer(vehicle.getEngineManufacturer());
        if (vehicle.getEngineType() != null) v.setEngineType(vehicle.getEngineType());
        if (vehicle.getDisplacementL() != null) v.setDisplacementL(vehicle.getDisplacementL());
        if (vehicle.getFuelType() != null) v.setFuelType(vehicle.getFuelType());
        if (vehicle.getBodyClass() != null) v.setBodyClass(vehicle.getBodyClass());
        if (vehicle.getBasePrice() != null) v.setBasePrice(vehicle.getBasePrice());
        if (vehicle.getDoors() != null) v.setDoors(vehicle.getDoors());
        if (vehicle.getTrim() != null) v.setTrim(vehicle.getTrim());
        if (vehicle.getTrim2() != null) v.setTrim2(vehicle.getTrim2());
        if (vehicle.getVehicleType() != null) v.setVehicleType(vehicle.getVehicleType());
        if (vehicle.getColor() != null) v.setColor(vehicle.getColor());

        if (vehicle.getCustomer() != null) v.setCustomer(customerService.findById(vehicle.getCustomer().getId()));

        return vehicleRepo.save(v);
    }

    @Override
    public void delete(long id) {
        findById(id);
        vehicleRepo.deleteById(id);
    }

    @Override
    public Vehicle decodeVIN(String vin) {
        String url = String.format("https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/%s?format=json", vin);

        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(converter);

        ParameterizedTypeReference<VinDecoderResponse> responseType = new ParameterizedTypeReference<>() {
        };
        VinDecoderResponse vd;
        try {
            ResponseEntity<VinDecoderResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            vd = responseEntity.getBody();
        } catch (HttpServerErrorException e) {
            vd = null;
            throw new RuntimeException(String.format("Error:Could not decode VIN:%s", vin), e);
        }

        assert vd != null;

        HashMap<String, String> results = vd.Results()[0];

        Vehicle v = new Vehicle();

        v.setVin(vin);
        v.setModelYear(Integer.parseInt(results.get("ModelYear")));
        v.setMake(results.get("Make"));
        v.setModel(results.get("Model"));
        v.setManufacturer(results.get("Manufacturer"));
        v.setPlantCompanyName(results.get("PlantCompanyName"));
        v.setPlantCity(results.get("PlantCity"));
        v.setPlantState(results.get("PlantState"));
        v.setTransmissionStyle(results.get("TransmissionStyle"));
        v.setDriveType(results.get("DriveType"));
        v.setEngineModel(results.get("EngineModel"));
        v.setEngineManufacturer(results.get("EngineManufacturer"));
        v.setEngineType(String.format(
                "%s %sCYL %s %s",
                results.get("EngineConfiguration"),
                results.get("EngineCylinders"),
                results.get("DisplacementL"),
                results.get("ValveTrainDesign")));
        v.setDisplacementL(results.get("DisplacementL"));
        v.setFuelType(results.get("FuelType"));
        v.setBodyClass(results.get("BodyClass"));
        v.setBasePrice(results.get("BasePrice"));
        v.setDoors(results.get("Doors"));
        v.setTrim(results.get("Trim"));
        v.setTrim2(results.get("Trim2"));
        v.setVehicleType(results.get("VehicleType"));

        return v;
    }

    @Override
    public Vehicle updateVehiclesCustomer(String vin, long id) {
        Vehicle vehicle = findByWholeVin(vin);
        Customer customer = customerService.findById(id);

        vehicle.setCustomer(customer);
        customer.getVehicles().add(vehicle);

        return vehicleRepo.save(vehicle);
    }
}
