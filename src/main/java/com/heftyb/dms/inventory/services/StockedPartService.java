package com.heftyb.dms.inventory.services;

import com.heftyb.dms.inventory.StockedPart;

import java.util.List;

public interface StockedPartService {
    List<StockedPart> findAll();

    StockedPart findById(long id);

    StockedPart save(StockedPart stockedPart);

    void delete(long id);
}
