package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointmentBlocks")
public class AppointmentBlock extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private LocalDateTime startDateTime;

    @Basic
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn
    private Employee advisor;

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
    }

    public AppointmentBlock(LocalDateTime startDateTime, Duration duration, Employee advisor) {
        this.startDateTime = startDateTime;
        this.duration = duration;
        endDateTime = startDateTime.plusMinutes(duration.toMinutes());
        this.advisor = advisor;
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
        return appointment == null;
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

    public Employee getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Employee advisor) {
        this.advisor = advisor;
    }

    public String startTimeCleanString() { return startDateTime.toLocalTime().toString().replace(":", ""); }
    public String endTimeCleanString() { return endDateTime.toLocalTime().toString().replace(":", ""); }

    @Override
    public String toString() {
        return "AppointmentBlock{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", isAvailable=" + isAvailable +
                ", duration=" + duration +
                '}';
    }
}
