package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.TechnicianFlatRateHour;
import com.heftyb.dms.repairorder.repositories.TechnicianFlatRateHoursRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "technicianFlatRateHourService")
public class TechnicianFlatRateHourServiceImp implements TechnicianFlatRateHourService {

    final private TechnicianFlatRateHoursRepository techRepo;
    final private EmployeeService employeeService;
    final private RepairOrderService repairOrderService;

    public TechnicianFlatRateHourServiceImp(final TechnicianFlatRateHoursRepository techRepo,
                                            final EmployeeService employeeService,
                                            final RepairOrderService repairOrderService) {
        this.techRepo = techRepo;
        this.employeeService = employeeService;
        this.repairOrderService = repairOrderService;
    }

    @Override
    public List<TechnicianFlatRateHour> findAll() {
        List<TechnicianFlatRateHour> hours = new ArrayList<>();
        techRepo.findAll().iterator().forEachRemaining(hours::add);
        return hours;
    }

    @Override
    public TechnicianFlatRateHour findById(long id) {
        return techRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "TechnicianFlatRateHourService Error: can not find technicianFlatRateHour id %g", id
                ))
        );
    }

    @Override
    public TechnicianFlatRateHour save(TechnicianFlatRateHour hour) {
        TechnicianFlatRateHour t = new TechnicianFlatRateHour();

        t.setTechnician(employeeService.findById(hour.getTechnician().getId()));
        t.setFlatRateHours(hour.getFlatRateHours());
        t.setJob(hour.getJob());
        return techRepo.save(t);
    }

    @Override
    public void delete(long id) {
        findById(id);
        techRepo.deleteById(id);
    }
}
