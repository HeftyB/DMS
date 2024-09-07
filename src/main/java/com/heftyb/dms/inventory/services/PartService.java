package com.heftyb.dms.inventory.services;

import com.heftyb.dms.inventory.Part;

import java.util.List;

public interface PartService {
    List<Part> findAll();

    Part findById(long id);

    Part save(Part part);

    void delete(long id);
}
