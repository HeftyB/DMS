package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.repairorder.WorkOrderJob;
import com.heftyb.dms.repairorder.WorkOrderStatus;

import java.util.List;

public interface WorkOrderJobService {
    List<WorkOrderJob> findAll();

    WorkOrderJob findById(long id);

    WorkOrderJob saveNew(WorkOrderJob job);

    WorkOrderJob saveNew(String concern, long roId);

    WorkOrderJob update(WorkOrderJob job);

    WorkOrderJob updateCause(long jobId, String cause);
    WorkOrderJob updateCorrection(long jobId, String correction);
    WorkOrderJob updateStatus(long jobId, WorkOrderStatus status);
    void delete(long id);
}
