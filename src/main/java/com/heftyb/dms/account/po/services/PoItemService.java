package com.heftyb.dms.account.po.services;

import com.heftyb.dms.account.po.POItem;

import java.util.List;

public interface PoItemService {
    List<POItem> findAll();
    POItem findById(long id);
    POItem save(POItem poItem);
    void delete(long id);
}
