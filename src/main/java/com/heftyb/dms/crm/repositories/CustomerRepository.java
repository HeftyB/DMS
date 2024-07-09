package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
