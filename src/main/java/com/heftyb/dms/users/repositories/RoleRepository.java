package com.heftyb.dms.users.repositories;

import com.heftyb.dms.users.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleIgnoreCase(String role);
}
