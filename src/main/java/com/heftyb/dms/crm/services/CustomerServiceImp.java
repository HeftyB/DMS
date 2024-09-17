package com.heftyb.dms.crm.services;

import com.heftyb.dms.account.invoice.repositories.InvoiceRepository;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.PhoneNumber;
import com.heftyb.dms.crm.repositories.CustomerRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.exceptions.ResourceFoundException;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImp implements CustomerService {

    final private CustomerRepository custRepo;
    final private InvoiceRepository invoiceRepo;

    public CustomerServiceImp(
            final CustomerRepository customerRepository,
            final InvoiceRepository invoiceRepo
    ) {
        custRepo = customerRepository;
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        custRepo.findAll().iterator().forEachRemaining(customers::add);
        return customers;
    }

    @Override
    public List<Customer> findByName(String firstName, String lastName) {
        return custRepo.findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCase(firstName, lastName);
    }

    @Override
    public List<Customer> findByFirstName(String firstName) {
        return custRepo.findByFirstNameContainingIgnoreCase(firstName);
    }

    @Override
    public List<Customer> findByLastName(String name) {
        return new ArrayList<>(custRepo.findByLastNameContainingIgnoreCase(name));
    }

    @Override
    public List<Customer> findByPhone(String phoneNum) {
        List<Customer> customers = findAll()
                .stream().filter(customer -> customer.getContactInformation().getPrimaryPhone().getNumber().contains(phoneNum)
                            || customer.getContactInformation().getAltPhone1().getNumber().contains(phoneNum)
                            || customer.getContactInformation().getAltPhone2().getNumber().contains(phoneNum)
                            || customer.getContactInformation().getFax().getNumber().contains(phoneNum))
                .collect(Collectors.toList());

        return new ArrayList<>(customers);
    }

    @Override
    public List<Customer> findByEmail(String email) {
        List<Customer> customers = new ArrayList<>();
        custRepo.findByEmailContainingIgnoreCase(email).iterator().forEachRemaining(customers::add);
        return customers;
    }

    @Override
    public Customer findById(long id) {

        return custRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format("CustomerService Error: customer id %s", id))
        );
    }

    @Override
    public Customer findByIdEditable(long id) {
        return custRepo.getById(id).orElseThrow(
                () -> new DataNotFoundException(String.format("CustomerService Error: customer id %s", id))
        );
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer newCustomer = new Customer();

        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setAddress(customer.getAddress());
        newCustomer.setContactInformation(customer.getContactInformation());
        newCustomer.setEmail(customer.getEmail());

        return custRepo.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {

        Customer c = findById(customer.getId());

        if (customer.getFirstName() != null) c.setFirstName(customer.getFirstName());
        if (customer.getLastName() != null) c.setLastName(customer.getLastName());
        if (customer.getAddress() != null) c.setAddress(customer.getAddress());
        if (customer.getContactInformation() != null) c.setContactInformation(customer.getContactInformation());
        if (customer.getEmail() != null) c.setEmail(customer.getEmail());

        if (!customer.getVehicles().isEmpty()) {
            throw new ResourceFoundException(
                    "Error: Could not update customer: Vehicles are not updated through customer, null value expected!"
            );
        }
        
        return custRepo.save(c);
    }


    @Override
    public void delete(long id) {
        findById(id);
        custRepo.deleteById(id);
    }
}
