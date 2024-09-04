package com.heftyb.dms.timekeeping.repositories;

import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TimeClockPunchSetRepository extends CrudRepository<TimeClockPunchSet, Long> {
    List<TimeClockPunchSet> findByEmployee(Employee employee);
    List<TimeClockPunchSet> findByEmployeeAndPayPeriod(Employee employee, PayPeriod period);
    List<TimeClockPunchSet> findByEmployeeAndDate(Employee employee, Date date);
}
