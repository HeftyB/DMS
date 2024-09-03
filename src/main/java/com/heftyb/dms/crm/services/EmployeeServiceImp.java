package com.heftyb.dms.crm.services;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.PhoneNumber;
import com.heftyb.dms.crm.repositories.EmployeeRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Transactional
@Service(value = "employeeService")
public class EmployeeServiceImp implements EmployeeService {

    final private EmployeeRepository empRepo;
    final private ContactService contactService;

    public EmployeeServiceImp(final EmployeeRepository empRepo,
                              final ContactService contactService) {
        this.empRepo = empRepo;
        this.contactService = contactService;
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
                ()-> new DataNotFoundException(String.format("EmployeeService Error: could not find employee id %s", id))
        );
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        Employee e = new Employee();
        e.setFirstName(employee.getFirstName());
        e.setLastName(employee.getLastName());
        e.setPreferredName(employee.getPreferredName());
        e.setTaxId(employee.getTaxId());
        e.setJobTitle(employee.getJobTitle());


        e = empRepo.save(e);
        employee.getMailingAddress().setEmployee(e);
        e.setMailingAddress(contactService.saveNewMailingAddress(employee.getMailingAddress()));


        for(PhoneNumber pn : employee.getPhoneNumbers()) {
            pn.setEmployee(e);
            e.getPhoneNumbers().add(contactService.saveNewPhoneNumber(pn));
        }

        return empRepo.save(e);
    }

    @Transactional
    @Override
    public void delete(long id) {
        findById(id);
        empRepo.deleteById(id);
    }

    @Override
    public void setEmployeeClockedIn(long id, boolean status) {
        Employee e = findById(id);
        e.setClockedIn(status);
        empRepo.save(e);
    }
}
