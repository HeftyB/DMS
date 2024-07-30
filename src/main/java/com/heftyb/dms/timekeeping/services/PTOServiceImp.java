package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.timekeeping.PTO;
import com.heftyb.dms.timekeeping.repositories.PTORepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "ptoService")
public class PTOServiceImp implements PTOService {

    final private PTORepository ptoRepo;
    final private EmployeeService employeeService;

    public PTOServiceImp(final PTORepository ptoRepo,
                         final EmployeeService employeeService){
        this.ptoRepo = ptoRepo;
        this.employeeService = employeeService;
    }

    @Override
    public List<PTO> findAll() {
        List<PTO> ptoList = new ArrayList<>();
        ptoRepo.findAll().iterator().forEachRemaining(ptoList::add);
        return ptoList;
    }

    @Override
    public PTO findById(long id) {
        return ptoRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "PTOService Error: can not find pto id %g", id
                ))
        );
    }

    @Override
    public PTO save(PTO pto) {
        PTO p = new PTO();
        p.setEmployee(employeeService.findById(pto.getEmployee().getId()));
        p.setApprovedBy(employeeService.findById(pto.getEmployee().getId()));
        p.setHours(p.getHours());
        p.setDate(pto.getDate());

        return ptoRepo.save(p);
    }

    @Override
    public void delete(long id) {
        findById(id);
        ptoRepo.deleteById(id);
    }
}
