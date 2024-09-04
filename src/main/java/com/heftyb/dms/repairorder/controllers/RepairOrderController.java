package com.heftyb.dms.repairorder.controllers;

import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.services.RepairOrderService;
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

    public RepairOrderController (final RepairOrderService rOService) { this.rOService = rOService; }

    @GetMapping({"/", ""})
    public String repairOrders(Principal principal, ModelMap map) {
        List<RepairOrder> ros = rOService.findAll();
        map.addAttribute("ros", ros);
        map.addAttribute("username", principal.getName());
        return "repair_orders";
    }
}
