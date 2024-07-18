package com.heftyb.dms.timekeeping;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "timeSheets")
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private ZonedDateTime payPeriodStart;
    private ZonedDateTime payPeriodEnd;

    private List<TimeClockPunchSet> timeClockPunchSets;
    private List<PTO> pto;

    private double totalHours;
}
