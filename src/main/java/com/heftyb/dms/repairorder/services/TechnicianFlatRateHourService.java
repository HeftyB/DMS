package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.repairorder.TechnicianFlatRateHour;

import java.util.List;

public interface TechnicianFlatRateHourService {
    List<TechnicianFlatRateHour> findAll();
    TechnicianFlatRateHour findById(long id);
    TechnicianFlatRateHour save(TechnicianFlatRateHour hour);
    void delete(long id);
}
