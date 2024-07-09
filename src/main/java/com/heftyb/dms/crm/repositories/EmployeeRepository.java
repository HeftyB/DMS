package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
