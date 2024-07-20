package com.heftyb.dms.timekeeping;

import com.heftyb.dms.crm.Employee;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "timepunchin")
public class TimePunchIn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Employee employee;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private TimePunchCode code;

    public TimePunchIn() {
    }

    public TimePunchIn(Employee employee, Date time, TimePunchCode code) {
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
