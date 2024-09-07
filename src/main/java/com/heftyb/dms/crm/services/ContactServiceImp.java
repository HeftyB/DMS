package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.MailingAddress;
import com.heftyb.dms.crm.PhoneNumber;
import com.heftyb.dms.crm.repositories.ContactInformationRepository;
import com.heftyb.dms.crm.repositories.MailingAddressRepository;
import com.heftyb.dms.crm.repositories.PhoneNumberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

//@Transactional
@Service(value = "contactService")
public class ContactServiceImp implements ContactService {

    private final ContactInformationRepository contactInformationRepository;
    private final MailingAddressRepository mailRepo;
    private final PhoneNumberRepository phoneRepo;

    public ContactServiceImp(final ContactInformationRepository contactInformationRepository,
                             final MailingAddressRepository mailRepo,
                             final PhoneNumberRepository phoneRepo) {
        this.contactInformationRepository = contactInformationRepository;
        this.mailRepo = mailRepo;
        this.phoneRepo = phoneRepo;
    }

    @Override
    public List<ContactInformation> findAllContactInformation() {
        List<ContactInformation> contactInformation = new ArrayList<>();
        contactInformationRepository.findAll().iterator().forEachRemaining(contactInformation::add);
        return contactInformation;
    }

    @Override
    public ContactInformation findContactInformationById(long id) {
        return contactInformationRepository.findById(id).orElseThrow(
                () -> new InvalidParameterException(errorString("contactInformation", id))
        );
    }

    @Transactional
    @Override
    public ContactInformation saveNewContactInformation(ContactInformation contactInformation) {
        ContactInformation c = new ContactInformation();
        c.setName(contactInformation.getName());
        c.setEmail(contactInformation.getEmail());
        c.setPhone(contactInformation.getPhone());
        c.setNotes(contactInformation.getNotes());
        return contactInformationRepository.save(c);
    }

    @Transactional
    @Override
    public void deleteContactInformation(long id) {
        findContactInformationById(id);
        contactInformationRepository.deleteById(id);
    }

    @Override
    public List<MailingAddress> findAllMailingAddress() {
        List<MailingAddress> mailingAddresses = new ArrayList<>();
        mailRepo.findAll().iterator().forEachRemaining(mailingAddresses::add);
        return mailingAddresses;
    }

    @Override
    public List<MailingAddress> findMailingAddressByAddress(String address) {
        List<MailingAddress> mailingAddresses = new ArrayList<>();
        mailRepo.findByAddressLine1ContainingIgnoreCase(address)
                .iterator().forEachRemaining(mailingAddresses::add);
        return mailingAddresses;
    }

    @Override
    public MailingAddress findMailingAddressById(long id) {
        return mailRepo.findById(id).orElseThrow(
                () -> new InvalidParameterException(errorString("mailingAddress", id))
        );
    }

    @Transactional
    @Override
    public MailingAddress saveNewMailingAddress(MailingAddress mailingAddress) {
        MailingAddress m = new MailingAddress();
        m.setName(mailingAddress.getName());
        m.setAddressLine1(mailingAddress.getAddressLine1());
        m.setAddressLine2(mailingAddress.getAddressLine2());
        m.setCity(mailingAddress.getCity());
        m.setState(mailingAddress.getState());
        m.setZip(mailingAddress.getZip());
        m.setEmployee(mailingAddress.getEmployee());
        m.setVendor(mailingAddress.getVendor());
        m.setCustomer(mailingAddress.getCustomer());

        return mailRepo.save(m);
    }

    @Transactional
    @Override
    public void deleteMailingAddress(long id) {
        findMailingAddressById(id);
        mailRepo.deleteById(id);
    }

    @Override
    public List<PhoneNumber> findAllPhoneNumbers() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneRepo.findAll().iterator().forEachRemaining(phoneNumbers::add);
        return phoneNumbers;
    }

    @Override
    public List<PhoneNumber> findPhoneNumbersByNumber(String number) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneRepo.findByNumberContaining(number).iterator().forEachRemaining(phoneNumbers::add);
        return phoneNumbers;
    }

    @Override
    public PhoneNumber findPhoneNumberById(long id) {
        return phoneRepo.findById(id).orElseThrow(
                () -> new InvalidParameterException(errorString("PhoneNumber", id))
        );
    }

    @Transactional
    @Override
    public PhoneNumber saveNewPhoneNumber(PhoneNumber phoneNumber) {
        PhoneNumber p = new PhoneNumber();
        p.setNumber(phoneNumber.getNumber());
        p.setType(phoneNumber.getType());
        p.setPrimary(phoneNumber.isPrimary());
        p.setCustomer(phoneNumber.getCustomer());
        p.setEmployee(phoneNumber.getEmployee());
        p.setLead(phoneNumber.getLead());
        p.setVendor(phoneNumber.getVendor());
        return phoneRepo.save(p);
    }

    @Override
    public List<PhoneNumber> findCustomersByPhoneNumbers(String number) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneRepo.findByNumberContainingAndAndCustomerNotNull(number).iterator().forEachRemaining(phoneNumbers::add);
        return phoneNumbers;
    }

    @Transactional
    @Override
    public void deletePhoneNumber(long id) {
        findPhoneNumberById(id);
        phoneRepo.deleteById(id);
    }

    private String errorString(String name, long id) {
        return String.format(
                "ContactService Error: could not find %s id %s", name, id
        );
    }
}
