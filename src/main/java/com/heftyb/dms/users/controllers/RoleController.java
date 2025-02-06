package com.heftyb.dms.users.controllers;

import com.heftyb.dms.users.Role;
import com.heftyb.dms.users.RoleDepartment;
import com.heftyb.dms.users.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {

    private final RoleService roleService;

    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping({"/", ""})
    public String getAllRoles(ModelMap map) {
        List<Role> roles = roleService.findAll();

        map.addAttribute("roles", roles);

        return "role_home";
    }

    @PostMapping({"/create", "/create/"})
    public String createNewRole(String role, String department, HttpServletRequest request) {
        Role r = new Role();
        r.setRole(role);
        r.setDepartment(RoleDepartment.valueOf(department));
        roleService.save(r);

        String refURL = request.getHeader("Referer");
        return "redirect:" + refURL;
    }

    @PostMapping({"/delete", "/delete/"})
    public String deleteRole(@RequestParam String roleId, HttpServletRequest request) {
        roleService.delete(Long.parseLong(roleId));

        String refURL = request.getHeader("Referer");
        return "redirect:" + refURL;
    }
}
