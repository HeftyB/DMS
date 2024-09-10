package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    List<Customer> findByName(String firstName, String lastName);

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String name);

    List<Customer> findByPhone(String phoneNum);

    Customer findById(long id);

    Customer findByIdEditable(long id);

    Customer saveNewCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void delete(long id);
}
