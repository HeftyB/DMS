package com.heftyb.dms.repairorder;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class BasicRepairOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private ZonedDateTime openDate;
    private ZonedDateTime closedDate;

    /* VEHICLE */

//    private String vin;
//    private String make;
//    private String model;
//    private String trim;
//    private String engine;

    private Vehicle vehicle;
    private int mileageIn;
    private int mileageOut;

    private Employee openedBy;
    private Employee technician;

    private boolean completed;

    private ArrayList<RepairOrderJob> jobs;

    private float subtotal;

    @OneToMany
    private ArrayList<Fee> fees;
}
