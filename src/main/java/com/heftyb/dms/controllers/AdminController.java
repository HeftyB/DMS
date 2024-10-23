package com.heftyb.dms.controllers;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final EmployeeService employeeService;

    public AdminController(final UserService userService,
                           final EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @GetMapping({"/", ""})
    public String adminHome(HttpServletRequest request, Model model, Principal principal) {
        User u = userService.findUserByUsername(principal.getName());
        model.addAttribute("user1", u);

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("username", principal.getName());
        return "hr_administrator";
    }

    @GetMapping({"/create_employee", "/create_employee/"})
    public String create_employee_form(Principal principal, ModelMap map) {
        map.addAttribute("username", principal.getName());

        return "employee_form";
    }

    @PostMapping({"/create_employee", "/create_employee/"})
    public String create_employee(Employee employee) {


        employeeService.save(employee);

        return "redirect:/admin/";
    }
}
