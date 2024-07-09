package com.heftyb.dms.timekeeping.repositories;

import com.heftyb.dms.timekeeping.TimeSheet;
import org.springframework.data.repository.CrudRepository;

public interface TimeSheetRepository extends CrudRepository<TimeSheet, Long> {
}
