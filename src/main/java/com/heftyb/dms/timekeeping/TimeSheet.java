package com.heftyb.dms.timekeeping;

import com.heftyb.dms.account.PayPeriod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "timeSheets")
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    @NotNull
    private PayPeriod payPeriod;

    @OneToMany(mappedBy = "timeSheet")
    private List<TimeClockPunchSet> timeClockPunchSets;

    @OneToMany(mappedBy = "timeSheet")
    private List<PTO> pto;

    private double totalHours;

    public TimeSheet() {
        timeClockPunchSets = new ArrayList<>();
        pto = new ArrayList<>();
    }

    public TimeSheet(PayPeriod payPeriod, List<TimeClockPunchSet> timeClockPunchSets, List<PTO> pto) {
        this.payPeriod = payPeriod;
        this.timeClockPunchSets = timeClockPunchSets;
        this.pto = pto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public PayPeriod getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(PayPeriod payPeriod) {
        this.payPeriod = payPeriod;
    }
}
