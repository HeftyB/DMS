package com.heftyb.dms.timekeeping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private ZonedDateTime payPeriodStart;
    private ZonedDateTime payPeriodEnd;

    private ArrayList<TimeClockPunchSet> timeClockPunchSets;
    private ArrayList<PTO> pto;

    private double totalHours;
}
