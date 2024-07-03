package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.TechnicianFlatRateHour;
import com.heftyb.dms.timekeeping.JobTimePunchSet;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "employees")
public class Employee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private String preferredName;

    private String address;
    private String city;
    private String state;
    private String zipcode;

    private String taxId;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private ArrayList<TimeClockPunchSet> timeClockPunchSets;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private ArrayList<JobTimePunchSet> jobTimePunchSets;

    private boolean clockedIn;
    private boolean jobInProgress;

    private JobTitle jobTitle;


//    private Employee manager;

//    private ArrayList<TimeSheet> timeSheets;
//    private ArrayList<PayCheck> payChecks;

    @OneToMany(mappedBy = "employee")
    private ArrayList<SaleLead> leads;

//    private ArrayList<CounterTicket> counterTickets;
//    private ArrayList<VehicleSale> vehicleSales;

    @OneToMany(mappedBy = "advisor", cascade = CascadeType.ALL)
    @JsonIgnore
    private ArrayList<RepairOrder> repairOrders;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
    @JsonIgnore
    private ArrayList<TechnicianFlatRateHour> flatRateHours;


    public Employee() {
    }

    public Employee(String firstName, String lastName, String preferredName, String address, String city, String state, String zipcode, String taxId, JobTitle jobTitle, Employee manager) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.taxId = taxId;
        this.jobTitle = jobTitle;
//        this.manager = manager;
        this.timeClockPunchSets = new ArrayList<>();
//        this.timeSheets = new ArrayList<>();
//        this.payChecks = new ArrayList<>();
//        this.repairOrderJobs = new ArrayList<>();
        this.flatRateHours = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public ArrayList<TimeClockPunchSet> getTimePunches() {
        return timeClockPunchSets;
    }

    public void setTimePunches(ArrayList<TimeClockPunchSet> timeClockPunchSets) {
        this.timeClockPunchSets = timeClockPunchSets;
    }

    public boolean isClockedIn() {
        return clockedIn;
    }

    public void setClockedIn(boolean clockedIn) {
        this.clockedIn = clockedIn;
    }

    public boolean isJobInProgress() {
        return jobInProgress;
    }

    public void setJobInProgress(boolean jobInProgress) {
        this.jobInProgress = jobInProgress;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public ArrayList<RepairOrder> getRepairOrders() {
        return repairOrders;
    }

    public void setRepairOrders(ArrayList<RepairOrder> repairOrders) {
        this.repairOrders = repairOrders;
    }

    public ArrayList<TechnicianFlatRateHour> getFlatRateHours() {
        return flatRateHours;
    }

    public void setFlatRateHours(ArrayList<TechnicianFlatRateHour> flatRateHours) {
        this.flatRateHours = flatRateHours;
    }

    //    public Employee getManager() {
//        return manager;
//    }
//
//    public void setManager(Employee manager) {
//        this.manager = manager;
//    }
//
//    public ArrayList<TimeSheet> getTimeSheets() {
//        return timeSheets;
//    }
//
//    public void setTimeSheets(ArrayList<TimeSheet> timeSheets) {
//        this.timeSheets = timeSheets;
//    }
}
