package com.heftyb.dms.account.repositories;

import com.heftyb.dms.account.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
