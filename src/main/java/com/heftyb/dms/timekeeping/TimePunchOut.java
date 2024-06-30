package com.heftyb.dms.timekeeping;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "timepunchout")
public class TimePunchOut {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Employee employee;

    private ZonedDateTime time;

    private com.heftyb.heftyrepairorder.models.TimePunchCode code;

    public TimePunchOut() {
    }

    public TimePunchOut(Employee employee, ZonedDateTime time, com.heftyb.heftyrepairorder.models.TimePunchCode code) {
        this.employee = employee;
        this.time = time;
        this.code = code;
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

    public com.heftyb.heftyrepairorder.models.TimePunchCode getCode() {
        return code;
    }

    public void setCode(com.heftyb.heftyrepairorder.models.TimePunchCode code) {
        this.code = code;
    }
}
