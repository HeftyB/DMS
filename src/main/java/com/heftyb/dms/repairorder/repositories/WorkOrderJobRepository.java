package com.heftyb.dms.repairorder.repositories;

import com.heftyb.dms.repairorder.WorkOrderJob;
import org.springframework.data.repository.CrudRepository;

public interface WorkOrderJobRepository extends CrudRepository <WorkOrderJob, Long> {
}
