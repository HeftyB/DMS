package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.repairorder.RepairOrderJob;

import java.util.List;

public interface RepairOrderJobService {
    List<RepairOrderJob> findAll();

    RepairOrderJob findById(long id);

    RepairOrderJob save(RepairOrderJob repairOrderJob);

    void delete(long id);
}
