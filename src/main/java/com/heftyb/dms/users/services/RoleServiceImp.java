package com.heftyb.dms.users.services;

import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.exceptions.ResourceFoundException;
import com.heftyb.dms.users.Role;
import com.heftyb.dms.users.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "roleService")
@Transactional
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepo;

    public RoleServiceImp(final RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        roleRepo.findAll().iterator().forEachRemaining(roles::add);
        return roles;
    }

    @Override
    public Role findById(long id) {
        return roleRepo.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("Role %s is not found", id)));
    }

    @Override
    public Role findByRole(String role) {
        return roleRepo.findByRoleIgnoreCase(role)
                .orElseThrow(() -> new DataNotFoundException(String.format("Role %s not found!", role)));
    }

    @Transactional
    @Override
    public Role save(Role role) {
        if (role.getUsers()
                .size() > 0) {
            throw new ResourceFoundException("User Roles are not updated through Role.");
        }
        Role r = new Role();
        r.setRole(role.getRole());

        return roleRepo.save(r);
    }

    @Override
    public void Delete(long id) {
        findById(id);
        roleRepo.deleteById(id);
    }
}
