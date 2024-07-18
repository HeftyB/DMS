package com.heftyb.dms.crm.services;

import com.heftyb.dms.account.invoice.repositories.InvoiceRepository;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.MailingAddress;
import com.heftyb.dms.crm.PhoneNumber;
import com.heftyb.dms.crm.repositories.CustomerRepository;
import com.heftyb.dms.crm.repositories.MailingAddressRepository;
import com.heftyb.dms.crm.repositories.PhoneNumberRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImp implements CustomerService{

    final private CustomerRepository custRepo;
    final private PhoneNumberRepository phoneRepo;
    final private VehicleService vehicleService;
    final private InvoiceRepository invoiceRepo;
    final private MailingAddressRepository mailRepo;

    public CustomerServiceImp (
            final CustomerRepository customerRepository,
            final PhoneNumberRepository phoneNumberRepository,
            final VehicleService vehicleService,
            final InvoiceRepository invoiceRepo,
            final MailingAddressRepository mailRepo
    ) {
        custRepo = customerRepository;
        phoneRepo = phoneNumberRepository;
        this.vehicleService = vehicleService;
        this.invoiceRepo = invoiceRepo;
        this.mailRepo = mailRepo;
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
        List<PhoneNumber> numbers = phoneRepo.findByNumberContainingAndAndCustomerNotNull(phoneNum);
        numbers.iterator().forEachRemaining(p -> {
            customers.add(p.getCustomer());
        });
        return customers;
    }

    @Override
    public Customer findById(long id) {

        return custRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format("CustomerService Error: customer id %g", id))
        );
    }

    @Override
    public Customer save(Customer customer) {
        Customer newCustomer;
//        if (customer.getId() != 0) {
//            newCustomer = custRepo.findById(customer.getId()).orElseThrow(
//                    ()-> new DataNotFoundException(
//                            String.format("CustomerService Error: customer id %g", customer.getId())
//                    )
//            )
//        } else {
            newCustomer = new Customer();
//        }

        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setEmail(customer.getEmail());
        MailingAddress m = mailRepo.save(new MailingAddress(
                customer.getMailingAddress().getAddressLine1(),
                customer.getMailingAddress().getAddressLine2(),
                customer.getMailingAddress().getCity(),
                customer.getMailingAddress().getState(),
                customer.getMailingAddress().getZip(),
                newCustomer
        ));
        newCustomer.setMailingAddress(m);

        List<PhoneNumber> phoneNumbers = new ArrayList<>();

        customer.getPhoneNumbers().iterator().forEachRemaining(phoneNumber -> {
            newCustomer.addPhone(
                    phoneRepo.save(new PhoneNumber(
                    phoneNumber.getNumber(),
                    phoneNumber.isPrimary(),
                    phoneNumber.getType(),
                    newCustomer
            ))
            );
        });

        customer.getVehicles().iterator().forEachRemaining(v -> {
            newCustomer.getVehicles().add(vehicleService.save(
                    new Vehicle(
                            v.getVin(),
                            v.getModelYear(),
                            v.getMake(),
                            v.getModel(),
                            v.getTrim(),
                            v.getEngine(),
                            v.getColor()

                    )
            ));
        });
        return custRepo.save(newCustomer);
    }



    @Override
    public void delete(long id) {
        findById(id);
        custRepo.deleteById(id);
    }
}
