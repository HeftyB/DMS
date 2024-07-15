package com.heftyb.dms.timekeeping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.ZonedDateTime;
import java.util.List;

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
