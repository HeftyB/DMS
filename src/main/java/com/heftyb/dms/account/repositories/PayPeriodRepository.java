package com.heftyb.dms.account.repositories;

import com.heftyb.dms.account.PayPeriod;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PayPeriodRepository extends CrudRepository<PayPeriod, Long> {
    Optional<PayPeriod> findByStartDate(Date date);
    Optional<PayPeriod> findByEndDate(Date date);
    List<PayPeriod> findAllByOrderByStartDateDesc();
}
