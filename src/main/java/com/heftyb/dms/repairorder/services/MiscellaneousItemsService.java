package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.repairorder.MiscellaneousItem;

import java.util.List;

public interface MiscellaneousItemsService {
    List<MiscellaneousItem> findAll();

    MiscellaneousItem findById(long id);

    MiscellaneousItem save(MiscellaneousItem item);

    void delete(long id);
}
