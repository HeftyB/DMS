package com.heftyb.dms.timekeeping.repositories;

import com.heftyb.dms.timekeeping.TimePunchOut;
import org.springframework.data.repository.CrudRepository;

public interface TimePunchOutRepository extends CrudRepository<TimePunchOut, Long> {
}
