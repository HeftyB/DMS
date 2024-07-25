package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.repositories.ContactInformationRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "contactInformationService")
public class ContactInformationServiceImp implements ContactInformationService{

    private final ContactInformationRepository contactInformationRepository;

    public ContactInformationServiceImp(final ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    @Override
    public List<ContactInformation> findAll() {
        List<ContactInformation> contactInformation = new ArrayList<>();
        contactInformationRepository.findAll().iterator().forEachRemaining(contactInformation::add);
        return contactInformation;
    }

    @Override
    public ContactInformation findById(long id) {
        return contactInformationRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "ContactInformationService Error: could not find contact info id %g", id
                ))
        );
    }

    @Override
    public ContactInformation save(ContactInformation contactInformation) {
        ContactInformation c = new ContactInformation();
        c.setName(contactInformation.getName());
        c.setEmail(contactInformation.getEmail());
        c.setPhone(contactInformation.getPhone());
        c.setNotes(contactInformation.getNotes());
        return contactInformationRepository.save(c);
    }

    @Override
    public void delete(long id) {
        findById(id);
        contactInformationRepository.deleteById(id);
    }
}
