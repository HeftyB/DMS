package com.heftyb.dms.account;

import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimeSheet;
import jakarta.persistence.*;

import java.io.Serial;
import java.time.Duration;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Entity
public class PayPeriod extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String period = Period.ofWeeks(2).toString();

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToMany(mappedBy = "payPeriod")
    List<TimeClockPunchSet> punchSets;

    @OneToMany(mappedBy = "payPeriod")
    List<TimeSheet> timeSheets;

    public PayPeriod() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Period getPeriodAsPeriod() {
        return Period.parse(period);
    }

    public void setPeriod(Period period) {
        this.period = period.toString();
    }

    public List<TimeClockPunchSet> getPunchSets() {
        return punchSets;
    }

    public void setPunchSets(List<TimeClockPunchSet> punchSets) {
        this.punchSets = punchSets;
    }

    public List<TimeSheet> getTimeSheets() {
        return timeSheets;
    }

    public void setTimeSheets(List<TimeSheet> timeSheets) {
        this.timeSheets = timeSheets;
    }

    @Override
    public String toString() {
        return "PayPeriod{" +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

