package com.heftyb.dms.vehicles.controllers;

import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController (final VehicleService vehicleService) { this.vehicleService = vehicleService; }

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
}
