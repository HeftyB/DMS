package com.heftyb.dms.controllers;

import com.heftyb.dms.appointments.ServiceMenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenController {

    private final ServiceMenuItemService serviceMenuItemService;

    public OpenController(final ServiceMenuItemService serviceMenuItemService) {
        this.serviceMenuItemService = serviceMenuItemService;
    }

    @GetMapping({"/", ""})
    public String index(ModelMap map) {
        map.addAttribute("items", serviceMenuItemService.findAll());
        return "index";
    }

    @GetMapping({"/services", "/services/"})
    public String services(ModelMap map) {
        map.addAttribute("items", serviceMenuItemService.findAll());
        return "services";
    }
}
