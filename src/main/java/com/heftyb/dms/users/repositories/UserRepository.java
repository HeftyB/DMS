package com.heftyb.dms.users.repositories;

import com.heftyb.dms.users.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
