package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.repositories.RepairOrderRepository;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.services.UserService;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "repairOrderService")
public class RepairOrderServiceImp implements RepairOrderService {

    private final RepairOrderRepository roRepo;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final EmployeeService employeeService;
    private final UserService userService;

    public RepairOrderServiceImp(final RepairOrderRepository roRepo,
                                 final CustomerService customerService,
                                 final VehicleService vehicleService,
                                 final EmployeeService employeeService,
                                 final UserService userService) {
        this.roRepo = roRepo;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.employeeService = employeeService;
        this.userService = userService;
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
                () -> new DataNotFoundException(String.format(
                        "RepairOrderService Error: could not find repair order id %s", id
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
    public void update(RepairOrder repairOrder) {
        RepairOrder ro = findById(repairOrder.getId());

        if (repairOrder.getStatus() != null) {
            ro.setStatus(repairOrder.getStatus());
        }

        if (repairOrder.getMileageIn() != 0) {
            ro.setMileageIn(repairOrder.getMileageIn());
        }


        if (repairOrder.getServiceTag() != null) {
            ro.setServiceTag(repairOrder.getServiceTag());
        }

        if (repairOrder.getAdvisor() != null) {
            ro.setAdvisor(employeeService.findById(repairOrder.getAdvisor().getId()));
        }

        if (repairOrder.getPriority() != null) {
            ro.setPriority(repairOrder.getPriority());
        }

        if (!repairOrder.getFees().isEmpty()) {
            ro.setFees(repairOrder.getFees());
        }

        if (!repairOrder.getMiscItems().isEmpty()) {
            ro.setMiscItems(repairOrder.getMiscItems());
        }

        roRepo.save(ro);
    }

    @Override
    public long createNew(String username, String vin, int mileageIn, String serviceTag, String priority) {
        User u = userService.findUserByUsername(username);
        List<Vehicle> vehicles = vehicleService.findByVin(vin);

        if (vehicles.size() != 1) {
            throw new InvalidParameterException(String.format("Could not find unique vehicle by VIN:%s!", vin));
        }


        RepairOrder ro = new RepairOrder(vehicles.get(0), mileageIn, serviceTag, u.getEmployee(), priority);

        ro = roRepo.save(ro);
        return ro.getId();
    }

    @Override
    public void delete(long id) {
        findById(id);
        roRepo.deleteById(id);
    }
}
