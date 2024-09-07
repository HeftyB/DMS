package com.heftyb.dms.timekeeping;

import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.crm.Employee;
import jakarta.persistence.*;

import java.time.Duration;
import java.util.Date;

@Entity
@Table(name = "timeClockPunchSets")
public class TimeClockPunchSet extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToOne
//    @NotNull
    @JoinColumn
    private TimePunchIn in;

    @OneToOne
    @JoinColumn
    private TimePunchOut out;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn
    private TimeSheet timeSheet;

    @ManyToOne
    @JoinColumn
    private PayPeriod payPeriod;

    public TimeClockPunchSet() {
    }

    public TimeClockPunchSet(Date date, TimePunchIn in, Employee employee, PayPeriod period) {
        this.date = date;
        this.in = in;
        this.employee = employee;
        payPeriod = period;
    }

    public TimeClockPunchSet(TimePunchIn timein) {
        this.in = timein;
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

    public TimeSheet getTimeSheet() {
        return timeSheet;
    }

    public void setTimeSheet(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }

    public PayPeriod getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(PayPeriod payPeriod) {
        this.payPeriod = payPeriod;
    }

    public Date getInPunchTime() {
        return in.getTime();
    }

    public long hoursBetweenPunches() {
        Duration d = Duration.between(in.getTime().toInstant(), out.getTime().toInstant());
        return d.toMinutes() / 60;
    }

    @Override
    public String toString() {
        return "TimeClockPunchSet{" +
                "id=" + id +
                ", date=" + date +
                ", in=" + in +
                ", out=" + out +
                ", employee=" + employee +
                '}';
    }
}
