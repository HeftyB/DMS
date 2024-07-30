package com.heftyb.dms.timekeeping;

import com.heftyb.dms.crm.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "timepunchout")
public class TimePunchOut {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne
    private Employee employee;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @NotNull
    private TimePunchCode code;

    public TimePunchOut() {
    }

    public TimePunchOut(Employee employee, Date time, TimePunchCode code) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public TimePunchCode getCode() {
        return code;
    }

    public void setCode(TimePunchCode code) {
        this.code = code;
    }
}
