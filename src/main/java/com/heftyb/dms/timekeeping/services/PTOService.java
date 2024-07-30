package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.timekeeping.PTO;

import java.util.List;

public interface PTOService {
    List<PTO> findAll();
    PTO findById(long id);
    PTO save(PTO pto);
    void delete(long id);
}
