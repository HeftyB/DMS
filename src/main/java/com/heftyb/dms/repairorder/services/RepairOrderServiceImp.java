package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.repositories.RepairOrderRepository;
import com.heftyb.dms.vehicles.services.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "repairOrderService")
public class RepairOrderServiceImp implements RepairOrderService{

    private final RepairOrderRepository roRepo;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final EmployeeService employeeService;

    public RepairOrderServiceImp(final RepairOrderRepository roRepo,
                                 final CustomerService customerService,
                                 final VehicleService vehicleService,
                                 final EmployeeService employeeService) {
        this.roRepo = roRepo;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.employeeService = employeeService;
    }

    @Override
    public List<RepairOrder> findAll() {
        List<RepairOrder> repairOrderList = new ArrayList<>();
        roRepo.findAll().iterator().forEachRemaining(repairOrderList::add);
        return repairOrderList;
    }

    @Override
    public RepairOrder findById(long id) {
        return roRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "RepairOrderService Error: could not find repair order id %g", id
                ))
        );
    }

    @Override
    public RepairOrder save(RepairOrder repairOrder) {
        RepairOrder r = new RepairOrder();
        r.setOpenDate(repairOrder.getOpenDate());
        r.setFinalizedDate(repairOrder.getFinalizedDate());
        r.setClosedDate(repairOrder.getClosedDate());
        r.setCustomer(customerService.findById(repairOrder.getCustomer().getId()));
        r.setVehicle(vehicleService.findById(repairOrder.getVehicle().getId()));
        r.setMileageIn(repairOrder.getMileageIn());
        r.setMileageOut(repairOrder.getMileageOut());
        r.setServiceTag(repairOrder.getServiceTag());
        r.setAdvisor(employeeService.findById(repairOrder.getAdvisor().getId()));
        r.setActive(repairOrder.isActive());
        r.setJobs(new ArrayList<>());
        r.setFees(new ArrayList<>());
        r.setMiscItems(new ArrayList<>());
        r.setTotalAmount(repairOrder.getTotalAmount());

        return roRepo.save(r);
    }

    @Override
    public void delete(long id) {
        findById(id);
        roRepo.deleteById(id);
    }
}
