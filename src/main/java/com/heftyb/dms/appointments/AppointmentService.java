package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.ContactInformation;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    List<Appointment> findAllAppointments();

    Appointment findAppointmentById(long id);

    List<Appointment> findByContactInfo(ContactInformation contactInformation);

    Appointment saveAppointment(Appointment appointment);

    void deleteAppointment(long id);

    List<AppointmentBlock> findAllBlocks();

    List<AppointmentBlock> findBlockByDate(LocalDate date);

    AppointmentBlock findBlockById(long id);

    AppointmentBlock saveBlock(AppointmentBlock block);

    void deleteBlock(long id);

}
