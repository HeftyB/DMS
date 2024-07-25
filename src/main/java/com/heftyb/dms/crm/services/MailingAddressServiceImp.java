package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.MailingAddress;
import com.heftyb.dms.crm.repositories.MailingAddressRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "mailingAddressService")
public class MailingAddressServiceImp implements MailingAddressService{

    private final MailingAddressRepository mailRepo;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final VendorService vendorService;

    public MailingAddressServiceImp(final  MailingAddressRepository mailRepo,
                                    final EmployeeService employeeService,
                                    final CustomerService customerService,
                                    final VendorService vendorService) {
        this.mailRepo = mailRepo;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.vendorService = vendorService;
    }

    @Override
    public List<MailingAddress> findAll() {
        List<MailingAddress> mailingAddresses = new ArrayList<>();
        mailRepo.findAll().iterator().forEachRemaining(mailingAddresses::add);
        return mailingAddresses;
    }

    @Override
    public List<MailingAddress> findByAddress(String address) {
        List<MailingAddress> mailingAddresses = new ArrayList<>();
        mailRepo.findByAddressLine1ContainingIgnoreCase(address)
                .iterator().forEachRemaining(mailingAddresses::add);
        return mailingAddresses;
    }

    @Override
    public MailingAddress findById(long id) {
        return mailRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format("MailingAddressService Error: could not find mailingAddress id %g", id))
        );
    }

    @Override
    public MailingAddress save(MailingAddress mailingAddress) {
        MailingAddress m = new MailingAddress();
        m.setName(mailingAddress.getName());
        m.setAddressLine1(mailingAddress.getAddressLine1());
        m.setAddressLine2(mailingAddress.getAddressLine2());
        m.setCity(mailingAddress.getCity());
        m.setState(mailingAddress.getState());
        m.setZip(mailingAddress.getZip());
        m.setCustomer(customerService.findById(mailingAddress.getCustomer().getId()));
        m.setEmployee(employeeService.findById(mailingAddress.getEmployee().getId()));
        m.setVendor(vendorService.findById(mailingAddress.getVendor().getId()));

        return mailRepo.save(m);
    }

    @Override
    public void delete(long id) {
        findById(id);
        mailRepo.deleteById(id);
    }
}
