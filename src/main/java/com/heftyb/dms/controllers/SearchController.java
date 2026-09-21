package com.heftyb.dms.controllers;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("search")
public class SearchController {
    private final VehicleService vehicleService;
    private final CustomerService customerService;

    public SearchController(final VehicleService vehicleService,
                            final CustomerService customerService) {
        this.vehicleService = vehicleService;
        this.customerService = customerService;
    }

    @GetMapping({"/", ""})
    public String results(Principal principal,
                          ModelMap map,
                          @RequestParam(required = false) Long custId,
                          @RequestParam(required = false, defaultValue = "") String fname,
                          @RequestParam(required = false, defaultValue = "") String lname,
                          @RequestParam(required = false, defaultValue = "") String phone,
                          @RequestParam(required = false, defaultValue = "") String email,
                          @RequestParam(required = false, defaultValue = "") String vin,
                          @RequestParam(required = false) boolean custSearch,
                          @RequestParam(required = false) boolean vehicleSearch) {
        map.addAttribute("username", principal.getName());

        List<Customer> customers = new ArrayList<>();
        List<Vehicle> vehicles = new ArrayList<>();

        if (custId != null && custId != 0) {
            Customer c = customerService.findById(custId);
            if (custSearch) customers.add(c);
            if (vehicleSearch) {
                c.getVehicles().iterator().forEachRemaining(vehicles::add);
            }
        }

        if (!fname.isEmpty() && !lname.isEmpty()) {
            List<Customer> c = customerService.findByName(fname, lname);

            if (custSearch) c.iterator().forEachRemaining(customers::add);
            if (vehicleSearch) {
                final List<Vehicle> finalVehicles = vehicles;
                c.stream().forEach(cust -> {
                    cust.getVehicles().iterator().forEachRemaining(finalVehicles::add);
                });
                finalVehicles.iterator().forEachRemaining(vehicles::add);
            }
        } else if (!lname.isEmpty()) {
            List<Customer> c = customerService.findByLastName(lname);

            if (custSearch) c.iterator().forEachRemaining(customers::add);
            if (vehicleSearch) {
                final List<Vehicle> finalVehicles = vehicles;
                c.stream().forEach(cust -> {
                    cust.getVehicles().iterator().forEachRemaining(finalVehicles::add);
                });
                vehicles = finalVehicles;
            }
        } else if (!fname.isEmpty()) {
            List<Customer> c = customerService.findByFirstName(fname);

            if (custSearch) c.iterator().forEachRemaining(customers::add);
            final List<Vehicle> finalVehicles = vehicles;
            c.stream().forEach(cust -> {
                cust.getVehicles().iterator().forEachRemaining(finalVehicles::add);
            });
            vehicles = finalVehicles;
        }

        if (!phone.isEmpty()) {
            List<Customer> customers1 = customerService.findByPhone(phone);
            if (custSearch) customers1.iterator().forEachRemaining(customers::add);
            final List<Vehicle> finalVehicles = vehicles;
            if (vehicleSearch) {
                customers1.iterator().forEachRemaining(customer -> customer.getVehicles().iterator().forEachRemaining(finalVehicles::add));
            }
            vehicles = finalVehicles;
        }

        if (!email.isEmpty()) {
            List<Customer> customers1 = customerService.findByEmail(email);
            if (custSearch) customers1.iterator().forEachRemaining(customers::add);
            for (Customer c : customers1) {
                for (Vehicle v : c.getVehicles()) {
                    vehicles.add(v);
                }
            }
        }

        if (!vin.isEmpty()) {
            List<Vehicle> vehicles1 = vehicleService.findByVin(vin);
            Set<Customer> cset = new HashSet<>();
            if (custSearch) {
                for (Vehicle v : vehicles1) {
                    cset.add(v.getCustomer());
                }

                cset.iterator().forEachRemaining(customers::add);
            }
            if (vehicleSearch) vehicles = vehicleService.findByVin(vin);
        }

        map.addAttribute("customers", customers);
        map.addAttribute("vehicles", vehicles);

        return "results";

    }
}
