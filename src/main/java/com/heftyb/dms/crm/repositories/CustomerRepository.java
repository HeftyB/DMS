package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastNameContainingIgnoreCase(String lastName);

    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);

    List<Customer> findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCase(String firstName, String lastName);

    List<Customer> findByEmailContainingIgnoreCase(String email);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Customer> findById(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Customer> getById(long id);

}
