package com.heftyb.dms.repairorder.controllers;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.WorkOrderJob;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import com.heftyb.dms.repairorder.services.WorkOrderJobService;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("repair_orders")
public class RepairOrderController {

    private final RepairOrderService rOService;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final WorkOrderJobService workOrderJobService;

    public RepairOrderController(final RepairOrderService rOService,
                                 final CustomerService customerService,
                                 final VehicleService vehicleService,
                                 final WorkOrderJobService workOrderJobService) {
        this.rOService = rOService;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.workOrderJobService = workOrderJobService;
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

    @GetMapping({"/repair_order", "/repair_order/"})
    public String details(Principal principal, ModelMap map, @RequestParam long id) {
        map.addAttribute("username", principal.getName());
        map.addAttribute("ro", rOService.findById(id));

        return "repair_order_details";
    }

    @PostMapping({"/repair_order", "/repair_order/"})
    public String addJob(Principal principal, @RequestParam long id, @RequestParam String concern) {
        workOrderJobService.saveNew(concern, id);

        return roIdRedirectString(id);
    }

    @GetMapping({"/repair_order/create", "/repair_order/create/"})
    public String newForm(Principal principal, ModelMap map, @RequestParam String vin) {
        map.addAttribute("username", principal.getName());
        map.addAttribute("vehicle", vehicleService.findByWholeVin(vin));

        return "new_repair_order";
    }

    @PostMapping({"/repair_order/create", "repair_order/create/"})
    public String createNew(Principal principal,
                            @RequestParam String vin,
                            @RequestParam int mileageIn,
                            @RequestParam String serviceTag,
                            @RequestParam String priority,
                            @RequestParam List<String> concerns) {
        long newId = rOService.createNew(principal.getName(), vin, mileageIn, serviceTag, priority);

        for (String s : concerns) {
            workOrderJobService.saveNew(s, newId);
        }

        return roIdRedirectString(newId);
    }

    private String roIdRedirectString(long id) {
        return String.format("redirect:/repair_orders/repair_order?id=%s", id);
    }
}
