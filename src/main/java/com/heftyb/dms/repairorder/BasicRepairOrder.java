package com.heftyb.dms.repairorder;

import com.heftyb.dms.account.models.fee.Fee;
import com.heftyb.dms.account.models.tax.TaxCharge;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;

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

    private Customer customer;

    private Vehicle vehicle;
    private int mileageIn;
    private int mileageOut;
    private String serviceTag;

    private Employee openedBy;
    private Employee technician;

    private boolean isCompleted;

    private ArrayList<RepairOrderJob> jobs;

    private float subtotal;

    @OneToMany
    private ArrayList<Fee> fees;

    private ArrayList<MiscellaneousItem> miscItems;

    @OneToOne
    @JoinColumn(name = "taxChargeId", referencedColumnName = "id")
    private TaxCharge taxes;

    private double totalAmount;

    public BasicRepairOrder() {
    }

    public BasicRepairOrder(ZonedDateTime openDate, ZonedDateTime closedDate, Customer customer, Vehicle vehicle, int mileageIn, int mileageOut, String serviceTag, Employee openedBy, ArrayList<RepairOrderJob> jobs) {
        this.openDate = openDate;
        this.closedDate = closedDate;
        this.customer = customer;
        this.vehicle = vehicle;
        this.mileageIn = mileageIn;
        this.mileageOut = mileageOut;
        this.serviceTag = serviceTag;
        this.openedBy = openedBy;
        this.jobs = jobs;
    }

}
