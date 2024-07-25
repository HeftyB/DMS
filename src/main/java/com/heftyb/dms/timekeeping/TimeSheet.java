package com.heftyb.dms.timekeeping;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "timeSheets")
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date payPeriodStart;

    @Temporal(TemporalType.DATE)
    private Date payPeriodEnd;

    @OneToMany(mappedBy = "timeSheet")
    private List<TimeClockPunchSet> timeClockPunchSets;

    @OneToMany(mappedBy = "timeSheet")
    private List<PTO> pto;

    private double totalHours;

    public TimeSheet() {
    }

    public TimeSheet(Date payPeriodStart, Date payPeriodEnd, List<TimeClockPunchSet> timeClockPunchSets, List<PTO> pto) {
        this.payPeriodStart = payPeriodStart;
        this.payPeriodEnd = payPeriodEnd;
        this.timeClockPunchSets = timeClockPunchSets;
        this.pto = pto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPayPeriodStart() {
        return payPeriodStart;
    }

    public void setPayPeriodStart(Date payPeriodStart) {
        this.payPeriodStart = payPeriodStart;
    }

    public Date getPayPeriodEnd() {
        return payPeriodEnd;
    }

    public void setPayPeriodEnd(Date payPeriodEnd) {
        this.payPeriodEnd = payPeriodEnd;
    }

    public List<TimeClockPunchSet> getTimeClockPunchSets() {
        return timeClockPunchSets;
    }

    public void setTimeClockPunchSets(List<TimeClockPunchSet> timeClockPunchSets) {
        this.timeClockPunchSets = timeClockPunchSets;
    }

    public List<PTO> getPto() {
        return pto;
    }

    public void setPto(List<PTO> pto) {
        this.pto = pto;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

}
