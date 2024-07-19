package com.heftyb.dms.timekeeping.repositories;

import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import org.springframework.data.repository.CrudRepository;

public interface TimeClockPunchSetRepository extends CrudRepository<TimeClockPunchSet, Long> {
}
