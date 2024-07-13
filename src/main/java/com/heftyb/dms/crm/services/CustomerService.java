package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    List<Customer> findByName(String name);
    List<Customer> findByPhone(String phoneNum);
    Customer findById(long id);
    Customer save(Customer customer);
    void delete(long id);
}
