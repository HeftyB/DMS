package com.heftyb.dms.crm.services;

import com.heftyb.dms.account.invoice.repositories.InvoiceRepository;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.PhoneNumber;
import com.heftyb.dms.crm.repositories.CustomerRepository;
import com.heftyb.dms.crm.repositories.PhoneNumberRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Transactional
@Service(value = "customerService")
public class CustomerServiceImp implements CustomerService {

    final private CustomerRepository custRepo;
    final private PhoneNumberRepository phoneRepo;
    final private VehicleService vehicleService;
    final private InvoiceRepository invoiceRepo;
    final private ContactService contactService;

    public CustomerServiceImp(
            final CustomerRepository customerRepository,
            final PhoneNumberRepository phoneNumberRepository,
            final VehicleService vehicleService,
            final InvoiceRepository invoiceRepo,
            final ContactService contactService
    ) {
        custRepo = customerRepository;
        phoneRepo = phoneNumberRepository;
        this.vehicleService = vehicleService;
        this.invoiceRepo = invoiceRepo;
        this.contactService = contactService;
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
        return custRepo.findByLastNameContainingIgnoreCase(firstName);
    }

    @Override
    public List<Customer> findByLastName(String name) {
        return new ArrayList<>(custRepo.findByLastNameContainingIgnoreCase(name));
    }

    @Override
    public List<Customer> findByPhone(String phoneNum) {
        List<Customer> customers = new ArrayList<>();
        List<PhoneNumber> numbers = contactService.findCustomersByPhoneNumbers(phoneNum);
        numbers.iterator().forEachRemaining(p -> {
            customers.add(p.getCustomer());
        });
        return customers;
    }

    @Override
    public Customer findById(long id) {

        return custRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format("CustomerService Error: customer id %g", id))
        );
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setEmail(customer.getEmail());

        newCustomer = custRepo.save(newCustomer);
        customer.getMailingAddress().setCustomer(newCustomer);
        newCustomer.setMailingAddress(contactService.saveNewMailingAddress(customer.getMailingAddress()));


        for (PhoneNumber pn : customer.getPhoneNumbers()) {
            pn.setCustomer(newCustomer);
            newCustomer.getPhoneNumbers().add(contactService.saveNewPhoneNumber(pn));
        }

        for (Vehicle vehicle : customer.getVehicles()) {
            vehicle.setCustomer(newCustomer);
            newCustomer.getVehicles().add(vehicleService.save(vehicle));
        }

        return custRepo.save(newCustomer);
    }


    @Transactional
    @Override
    public void delete(long id) {
        findById(id);
        custRepo.deleteById(id);
    }
}
