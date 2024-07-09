package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
