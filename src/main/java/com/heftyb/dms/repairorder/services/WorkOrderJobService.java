package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.repairorder.WorkOrderJob;

import java.util.List;

public interface WorkOrderJobService {
    List<WorkOrderJob> findAll();

    WorkOrderJob findById(long id);

    WorkOrderJob saveNew(WorkOrderJob job);

    WorkOrderJob saveNew(String concern, long roId);

    WorkOrderJob update(WorkOrderJob job);

    void delete(long id);
}
