package com.heftyb.dms.controllers;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.JobTitle;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.users.RoleDepartment;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.services.RoleService;
import com.heftyb.dms.users.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final EmployeeService employeeService;
    private final RoleService roleService;

    public AdminController(final UserService userService,
                           final EmployeeService employeeService,
                           final RoleService roleService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping({"/", ""})
    public String adminHome(HttpServletRequest request, Model model, Principal principal) {
        User u = userService.findUserByUsername(principal.getName());
        model.addAttribute("user1", u);

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("username", principal.getName());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("deptRoles", RoleDepartment.values());
        model.addAttribute("jobTitles", JobTitle.values());
        return "hr_administrator";
    }

    @GetMapping({"/create_employee", "/create_employee/"})
    public String create_employee_form(Principal principal, ModelMap map) {
        map.addAttribute("username", principal.getName());

        return "employee_form";
    }

    @PostMapping({"/create_employee", "/create_employee/"})
    public String create_employee(@Valid Employee employee) {


        employeeService.save(employee);

        return "redirect:/admin/";
    }
}
