package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.repositories.CustomerRepository;
import com.heftyb.dms.crm.repositories.PhoneNumberRepository;
import com.heftyb.dms.vehicles.services.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImp implements CustomerService{

    final private CustomerRepository custRepo;
    final private PhoneNumberRepository phoneRepo;
    final private VehicleService vehicleService;

    public CustomerServiceImp (
            final CustomerRepository customerRepository,
            final PhoneNumberRepository phoneNumberRepository,
            final VehicleService vehicleService
    ) {
        custRepo = customerRepository;
        phoneRepo = phoneNumberRepository;
        this.vehicleService = vehicleService;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public List<Customer> findByName(String name) {
        return null;
    }

    @Override
    public List<Customer> findByPhone(String phoneNum) {
        return null;
    }

    @Override
    public Customer findById(long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
