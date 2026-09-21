package com.heftyb.dms.vehicles.controllers;

import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping({"/", ""})
    public String vehicles_home(Principal principal, ModelMap map) {
        map.addAttribute("username", principal.getName());
        map.addAttribute("vehicles", vehicleService.findAll());

        return "vehicles";
    }

    @GetMapping({"/vehicle", "/vehicle/"})
    public String vehicle_details(Principal principal, ModelMap map, @RequestParam long id) {
        map.addAttribute("username", principal.getName());
        map.addAttribute("vehicle", vehicleService.findById(id));

        return "vehicle_details";
    }

    // The shared vin-search fragment submits with POST, so this accepts both methods.
    @RequestMapping(value = {"/search", "/search/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String vin_search(Principal principal, ModelMap map, @RequestParam String vin) {
        map.addAttribute("username", principal.getName());
        if (vin.isBlank()) {
            return "redirect:/vehicles";
        }

        List<Vehicle> vehicles = vehicleService.findByVin(vin);
        map.addAttribute("vehicles", vehicles);

        return "vehicles";
    }

    @PostMapping({"/vehicle/create", "/vehicle/create/"})
    public String create_new(String vin) {
        Vehicle v = vehicleService.decodeVIN(vin);
        v = vehicleService.saveNew(v);

        return String.format("redirect:/vehicles/vehicle?id=%s", v.getId());
    }

    @PostMapping({"/vehicle/customer", "/vehicle/customer/"})
    public String update_vehicles_customer(Principal principal, @RequestParam String vin, @RequestParam long newCustomerId) {
        Vehicle v = vehicleService.updateVehiclesCustomer(vin, newCustomerId);
        return String.format("redirect:/vehicles/vehicle?id=%s", v.getId());
    }

    @PostMapping({"/customer/new", "/customer/new/"})
    public String save_customers_new_vehicle(Principal principal, @RequestParam String vin, @RequestParam long custId) {

        List<Vehicle> vehicles = vehicleService.findByVin(vin);

        if (vehicles.isEmpty()) {
            Vehicle v = vehicleService.decodeVIN(vin);
            v = vehicleService.saveNew(v);
            v = vehicleService.updateVehiclesCustomer(v.getVin(), custId);
        } else {
            vehicleService.updateVehiclesCustomer(vehicles.get(0).getVin(), custId);
        }
        return String.format("redirect:/customers/customer?id=%s", custId);
    }
}
