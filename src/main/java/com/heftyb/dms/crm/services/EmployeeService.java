package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(long id);

    Employee save(Employee employee);

    void delete(long id);

    void setEmployeeClockedIn(long id, boolean status);

    List<Employee> getActiveAdvisors();
}
