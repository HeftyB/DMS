package com.heftyb.dms.users.controllers;

import com.heftyb.dms.users.Role;
import com.heftyb.dms.users.RoleDepartment;
import com.heftyb.dms.users.services.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping({"/create", "/create/"})
    public String createNewRole(@RequestParam String role, @RequestParam RoleDepartment department) {
        Role r = new Role();
        r.setRole(role);
        r.setDepartment(department);
        roleService.save(r);

        return "redirect:/admin";
    }

    @PostMapping({"/delete", "/delete/"})
    public String deleteRole(@RequestParam long roleId) {
        roleService.delete(roleId);

        return "redirect:/admin";
    }
}
