package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.MailingAddress;
import com.heftyb.dms.crm.repositories.EmployeeRepository;
import com.heftyb.dms.crm.repositories.MailingAddressRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "employeeService")
public class EmployeeServiceImp implements EmployeeService {

    final private EmployeeRepository empRepo;
    final private MailingAddressRepository mailRepo;

    public EmployeeServiceImp(final EmployeeRepository empRepo,
                              final MailingAddressRepository mailRepo) {
        this.empRepo = empRepo;
        this.mailRepo = mailRepo;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        empRepo.findAll().iterator().forEachRemaining(employees::add);
        return employees;
    }

    @Override
    public Employee findById(long id) {
        return empRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format("EmployeeService Error: could not find employee id %g", id))
        );
    }

    @Override
    public Employee save(Employee employee) {
        Employee e = new Employee();
        e.setFirstName(employee.getFirstName());
        e.setLastName(employee.getLastName());
        e.setPreferredName(employee.getPreferredName());
        employee.getMailingAddress().setEmployee(e);

        MailingAddress m = new MailingAddress();
        m.setName(employee.getMailingAddress().getName());
        m.setAddressLine1(employee.getMailingAddress().getAddressLine1());
        m.setAddressLine2(employee.getMailingAddress().getAddressLine2());
        m.setCity(employee.getMailingAddress().getCity());
        m.setState(employee.getMailingAddress().getState());
        m.setZip(employee.getMailingAddress().getZip());
        m.setEmployee(e);
        m = mailRepo.save(m);

        e.setMailingAddress(m);

        e.setTaxId(employee.getTaxId());

        e.setTimeClockPunchSets(new ArrayList<>());
        e.setJobTimePunchSets(new ArrayList<>());

        e.setClockedIn(employee.isClockedIn());
        e.setJobInProgress(employee.isJobInProgress());
        e.setJobTitle(employee.getJobTitle());
        e.setRepairOrders(new ArrayList<>());
        e.setFlatRateHours(new ArrayList<>());

        return empRepo.save(e);
    }

    @Override
    public void delete(long id) {
        findById(id);
        empRepo.deleteById(id);
    }
}
