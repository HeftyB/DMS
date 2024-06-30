package com.heftyb.dms.timekeeping;

import com.heftyb.dms.crm.Employee;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.ZonedDateTime;

public class PTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Employee employee;
    private Employee approvedBy;

    private double hours;
    private ZonedDateTime date;


    private TimeSheet timeSheet;
}
