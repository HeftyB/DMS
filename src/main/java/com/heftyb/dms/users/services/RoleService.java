package com.heftyb.dms.users.services;

import com.heftyb.dms.users.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(long id);
    Role findByRole(String role);
    Role save(Role role);
    void Delete(long id);
}
