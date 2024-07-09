package com.heftyb.dms.timekeeping.repositories;

import com.heftyb.dms.timekeeping.TimePunchIn;
import org.springframework.data.repository.CrudRepository;

public interface TimePunchInRepository extends CrudRepository<TimePunchIn, Long> {
}
