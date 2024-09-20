package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.WorkOrderJob;

import java.util.List;

public interface RepairOrderService {
    List<RepairOrder> findAll();

    RepairOrder findById(long id);

    RepairOrder save(RepairOrder repairOrder);

    void update(RepairOrder repairOrder);

    long createNew(String username, String vin, int mileageIn, String serviceTag, String priority);

    void delete(long id);
}
