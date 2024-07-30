package com.heftyb.dms.crm.repositories;

import com.heftyb.dms.crm.MailingAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MailingAddressRepository extends CrudRepository<MailingAddress, Long> {
    List<MailingAddress> findByAddressLine1ContainingIgnoreCase(String address);
}
