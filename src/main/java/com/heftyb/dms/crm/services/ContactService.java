package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.MailingAddress;
import com.heftyb.dms.crm.PhoneNumber;

import java.util.List;

public interface ContactService {
    List<ContactInformation> findAllContactInformation();
    ContactInformation findContactInformationById(long id);
    ContactInformation saveNewContactInformation(ContactInformation contactInformation);
    void deleteContactInformation(long id);

    List<MailingAddress> findAllMailingAddress();
    List<MailingAddress> findMailingAddressByAddress(String address);
    MailingAddress findMailingAddressById(long id);
    MailingAddress saveNewMailingAddress(MailingAddress mailingAddress);
    void deleteMailingAddress(long id);

    List<PhoneNumber> findAllPhoneNumbers();
    List<PhoneNumber> findPhoneNumbersByNumber(String number);
    PhoneNumber findPhoneNumberById(long id);
    PhoneNumber saveNewPhoneNumber(PhoneNumber phoneNumber);
    List<PhoneNumber> findCustomersByPhoneNumbers(String number);
    void deletePhoneNumber(long id);
}
