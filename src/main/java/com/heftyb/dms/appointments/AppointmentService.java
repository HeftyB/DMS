package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.Employee;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    int defaultInterval = 15;

    List<Appointment> findAllAppointments();

    Appointment findAppointmentById(long id);

    List<Appointment> findAppointmentsByDate(LocalDate date);

    List<Appointment> findByContactInfo(ContactInformation contactInformation);

    Appointment saveAppointment(Appointment appointment);

    void deleteAppointment(long id);

    List<AppointmentBlock> findAllBlocks();

    List<AppointmentBlock> findBlockByDate(LocalDate date, List<Employee> advisors);

    AppointmentBlock findBlockById(long id);

    AppointmentBlock saveBlock(AppointmentBlock block);

    void deleteBlock(long id);

    List<AppointmentBlock> createNewBlocksForDate(LocalDate date, Duration duration, List<Employee> advisors);

}
