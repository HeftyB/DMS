package com.heftyb.dms.appointments;

import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
