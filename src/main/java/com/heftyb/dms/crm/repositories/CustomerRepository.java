package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
    List<Customer> findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCase(String firstName, String lastName);
}
