package com.heftyb.dms.account.models.repositories;

import com.heftyb.dms.account.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
