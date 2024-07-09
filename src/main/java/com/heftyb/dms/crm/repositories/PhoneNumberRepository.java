package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.PhoneNumber;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
    List<PhoneNumber> findByNumberContaining(String number);
}
