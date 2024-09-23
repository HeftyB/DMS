package com.heftyb.dms.timekeeping;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.repairorder.WorkOrderJob;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "jobTimePunchSets")
public class JobTimePunchSet extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToOne
    @JoinColumn()
    private TimePunchIn in;

    @OneToOne
    @JoinColumn()
    private TimePunchOut out;

    @ManyToOne()
    @JoinColumn()
    private Employee employee;

    @ManyToOne()
    @JoinColumn()
    private WorkOrderJob job;

    public JobTimePunchSet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TimePunchIn getIn() {
        return in;
    }

    public void setIn(TimePunchIn in) {
        this.in = in;
    }

    public TimePunchOut getOut() {
        return out;
    }

    public void setOut(TimePunchOut out) {
        this.out = out;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public WorkOrderJob getJob() {
        return job;
    }

    public void setJob(WorkOrderJob job) {
        this.job = job;
    }
}
