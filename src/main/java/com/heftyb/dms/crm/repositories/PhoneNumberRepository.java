package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.PhoneNumber;
import org.springframework.data.repository.CrudRepository;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
    Iterable<PhoneNumber> findByNumberContaining(String number);

    Iterable<PhoneNumber> findByNumberContainingAndAndCustomerNotNull(String number);
}
