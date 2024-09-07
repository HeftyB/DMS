package com.heftyb.dms.repairorder.controllers;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("repair_orders")
public class RepairOrderController {

    private final RepairOrderService rOService;
    private final CustomerService customerService;
    private final VehicleService vehicleService;

    public RepairOrderController(final RepairOrderService rOService,
                                 final CustomerService customerService,
                                 final VehicleService vehicleService) {
        this.rOService = rOService;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
    }

    @GetMapping({"/", ""})
    public String repairOrders(Principal principal, ModelMap map) {
        List<RepairOrder> ros = rOService.findAll();
        map.addAttribute("ros", ros);
        map.addAttribute("username", principal.getName());
        return "repair_orders";
    }

    @GetMapping({"/search", "/search/"})
    public String search() {
        return "search";
    }

    @GetMapping({"/results", "/results/"})
    public String results(Principal principal, ModelMap map) {
        List<Customer> customers = customerService.findAll();
        List<Vehicle> vehicles = vehicleService.findAll();

        map.addAttribute("username", principal.getName());
        map.addAttribute("customers", customers);
        map.addAttribute("vehicles", vehicles);

        return "results";
    }
}
