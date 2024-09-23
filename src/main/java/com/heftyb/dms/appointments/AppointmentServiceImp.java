package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImp implements AppointmentService {

    private final AppointmentRepository apptRepo;
    private final AppointmentBlockRepository blockRepo;
    private final EmployeeService employeeService;

    public AppointmentServiceImp(final AppointmentRepository appointmentRepository,
                                 final AppointmentBlockRepository appointmentBlockRepository,
                                 final EmployeeService employeeService) {
        apptRepo = appointmentRepository;
        blockRepo = appointmentBlockRepository;
        this.employeeService = employeeService;
    }


    @Override
    public List<Appointment> findAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        apptRepo.findAll().iterator().forEachRemaining(appointments::add);
        return appointments;
    }

    @Override
    public Appointment findAppointmentById(long id) {
        return apptRepo.findById(id).orElseThrow(() -> new DataNotFoundException(
                String.format("Could not find Appointment id: %s", id)
        ));
    }

    @Override
    public List<Appointment> findByContactInfo(ContactInformation contactInformation) {
        List<Appointment> appointments = new ArrayList<>();
        apptRepo.findAll().iterator().forEachRemaining(appointments::add);
        appointments.stream().filter(a -> a.getContactInformation().containsMatchingContactInformation(contactInformation));

        return appointments;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        Appointment a;

        if (appointment.getId() != 0) {
            a = findAppointmentById(appointment.getId());
        } else {
            a = new Appointment();
        }

        a.setStartDateTime(appointment.getStartDateTime());
        a.setEndDateTime(appointment.getEndDateTime());
        Employee e = employeeService.findById(appointment.getAdvisor().getId());
        a.setAdvisor(e);
        a.setConcerns(appointment.getConcerns());

        for (AppointmentBlock block : appointment.getBlocks()) {
            AppointmentBlock b = findBlockById(block.getId());

            b.setAppointment(a);
            a.getBlocks().add(b);
        }

        a.setContactInformation(appointment.getContactInformation());
        a.setConfirmationCode(appointment.getConfirmationCode());

        return apptRepo.save(a);
    }

    @Override
    public void deleteAppointment(long id) {
        findAppointmentById(id);
        apptRepo.deleteById(id);
    }

    @Override
    public List<AppointmentBlock> findAllBlocks() {
        List<AppointmentBlock> blocks = new ArrayList<>();
        blockRepo.findAll().iterator().forEachRemaining(blocks::add);
        return blocks;
    }

    @Override
    public List<AppointmentBlock> findBlockByDate(LocalDate date) {
        List<AppointmentBlock> blocks = findAllBlocks();
        blocks.stream().filter(b -> b.getStartDateTime().toLocalDate() == date);
        return blocks;
    }

    @Override
    public AppointmentBlock findBlockById(long id) {
        return blockRepo.findById(id).orElseThrow(() -> new DataNotFoundException(
                String.format("Could not find AppointmentBlock id: %s", id)
        ));
    }

    @Override
    public AppointmentBlock saveBlock(AppointmentBlock block) {
        AppointmentBlock b;

        if (block.getId() != 0) {
            b = findBlockById(block.getId());
        } else {
            b = new AppointmentBlock();
        }

        b.setStartDateTime(block.getStartDateTime());
        b.setEndDateTime(block.getEndDateTime());
        b.setAppointment(block.getAppointment());
        b.setAvailable(block.isAvailable());
        b.setDuration(block.getDuration());
        return blockRepo.save(b);
    }

    @Override
    public void deleteBlock(long id) {
        findBlockById(id);
        blockRepo.deleteById(id);
    }
}

