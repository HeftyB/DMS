package com.heftyb.dms.timekeeping;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "timePunchIn")
public class TimePunchIn extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Employee employee;

    @NotNull
    private LocalDateTime time;

    private TimePunchCode code;

    public TimePunchIn() {
    }

    public TimePunchIn(Employee employee, LocalDateTime time, TimePunchCode code) {
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public TimePunchCode getCode() {
        return code;
    }

    public void setCode(TimePunchCode code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TimePunchIn{" +
                "id=" + id +
                ", time=" + time +
                ", code=" + code +
                '}';
    }
}
