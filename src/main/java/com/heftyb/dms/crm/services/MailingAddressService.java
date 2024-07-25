package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.MailingAddress;

import java.util.List;

public interface MailingAddressService {
    List<MailingAddress> findAll();
    List<MailingAddress> findByAddress(String address);
    MailingAddress findById(long id);
    MailingAddress save(MailingAddress mailingAddress);
    void delete(long id);
}
