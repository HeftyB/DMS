package com.heftyb.dms.crm.controllers;

import com.heftyb.dms.crm.*;
import com.heftyb.dms.crm.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"", "/"})
    public String customerHome(Principal principal, ModelMap map) {
        map.addAttribute("username", principal.getName());
        map.addAttribute("customers", customerService.findAll());
        return "customer_home";
    }

    @GetMapping({"/customer", "/customer/"})
    public String customerDetails(Principal principal,
                                  @RequestParam long id,
                                  @RequestParam(required = false) boolean editable,
                                  ModelMap map) {
        map.addAttribute("username", principal.getName());


        Customer customer;

        if (editable) customer = customerService.findByIdEditable(id);
        else customer = customerService.findById(id);

        map.addAttribute("customer", customer);
        map.addAttribute("editable", editable);
        map.addAttribute("action", String.format("/customers/customer?id=%s", id));
        map.addAttribute("hide_buttons", false);
        map.addAttribute("phoneTypes", PhoneNumberType.values());

        return "customer_details";
    }


    @PostMapping(value = "/customer", consumes = {"application/x-www-form-urlencoded"})
    public String saveEdit(@RequestParam long id, ModelMap map, @Valid Customer customer) {
        customerService.updateCustomer(customer);

        return String.format("redirect:/customers/customer?id=%s", id);
    }

    @PostMapping({"/customer/create", "/customer/create/"})
    public String createNew(Principal principal, @Valid Customer customer) {
        Customer c = customerService.saveNewCustomer(customer);
        return String.format("redirect:/customers/customer?id=%s", c.getId());
    }

    @GetMapping({"/customer/create", "/customer/create/"})
    public String createNewForm(Principal principal, ModelMap map) {
        map.addAttribute("username", principal.getName());
        Customer c = new Customer();
        c.setAddress(new Address());
        c.getAddress().setZip(new Zipcode());
        c.setContactInformation(new ContactInformation());
        c.getContactInformation().setPrimaryPhone(new PhoneNumber());
        c.getContactInformation().setAltPhone1(new PhoneNumber());
        c.getContactInformation().setAltPhone2(new PhoneNumber());
        c.getContactInformation().setFax(new PhoneNumber());
        map.addAttribute("customer", c);
        map.addAttribute("editable", true);
        map.addAttribute("new", true);
        return "create_customer";
    }
}
