package com.heftyb.dms.timekeeping;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "timepunchin")
public class TimePunchIn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Employee employee;

    private ZonedDateTime time;

    private TimePunchCode code;

    public TimePunchIn() {
    }

    public TimePunchIn(Employee employee, ZonedDateTime time, TimePunchCode code) {
        this.employee = employee;
        this.time = time;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public TimePunchCode getCode() {
        return code;
    }

    public void setCode(TimePunchCode code) {
        this.code = code;
    }
}
