package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.ContactInformation;

import java.util.List;

public interface ContactInformationService {
    List<ContactInformation> findAll();
    ContactInformation findById(long id);
    ContactInformation save(ContactInformation contactInformation);
    void delete(long id);
}
