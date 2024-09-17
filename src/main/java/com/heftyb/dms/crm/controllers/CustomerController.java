package com.heftyb.dms.crm.controllers;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Address;
import com.heftyb.dms.crm.PhoneNumberType;
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
        map.addAttribute("phoneTypes", PhoneNumberType.values());

        return "customer_details";
    }

//    @PostMapping(value = {"/customer", "/customer/"},
//    consumes = {"application/x-www-form-urlencoded;charset=UTF-8"})
//    consumes = {"application/*"})
//    consumes = {"application/x-www-form-urlencoded"})

    @PostMapping(value = "/customer", consumes = {"application/x-www-form-urlencoded"})
    public String saveEdit(@RequestParam long id, ModelMap map, @Valid Customer customer) {
//        map.addAttribute("username", principal.getName());
        customerService.updateCustomer(customer);
//        Customer c = customerService.saveNewCustomer(customer);
        System.out.println(customer);
//        map.addAttribute("customer", customer);

        return String.format("redirect:/customers/customer?id=%s", id);
    }
}
