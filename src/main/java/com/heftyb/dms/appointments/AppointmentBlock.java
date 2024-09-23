package com.heftyb.dms.appointments;

import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointmentBlocks")
public class AppointmentBlock extends Auditable {

    @Id
    @GeneratedValue
    private long id;

    @Basic
    private LocalDateTime startDateTime;

    @Basic
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn
    private Appointment appointment;

    private boolean isAvailable;

    private Duration duration;

    public AppointmentBlock() {
    }

    public AppointmentBlock(LocalDateTime startDateTime, Duration duration) {
        this.startDateTime = startDateTime;
        this.duration = duration;
        endDateTime = startDateTime.plusMinutes(duration.toMinutes());
        isAvailable = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime start) {
        this.startDateTime = start;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime end) {
        this.endDateTime = end;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
