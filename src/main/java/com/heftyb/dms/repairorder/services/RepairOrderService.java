package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.repairorder.RepairOrder;

import java.util.List;

public interface RepairOrderService {
    List<RepairOrder> findAll();

    RepairOrder findById(long id);

    RepairOrder save(RepairOrder repairOrder);

    void delete(long id);
}
