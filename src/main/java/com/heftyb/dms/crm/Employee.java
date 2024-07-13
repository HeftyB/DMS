package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.TechnicianFlatRateHour;
import com.heftyb.dms.timekeeping.JobTimePunchSet;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

@Entity
@Table(name = "employees")
public class Employee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String preferredName;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private MailingAddress mailingAddress;

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

    public Employee(String firstName, String lastName, String preferredName, MailingAddress mailingAddress, String taxId, JobTitle jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.mailingAddress = mailingAddress;
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

    public MailingAddress getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(MailingAddress mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public ArrayList<TimeClockPunchSet> getTimeClockPunchSets() {
        return timeClockPunchSets;
    }

    public void setTimeClockPunchSets(ArrayList<TimeClockPunchSet> timeClockPunchSets) {
        this.timeClockPunchSets = timeClockPunchSets;
    }

    public ArrayList<JobTimePunchSet> getJobTimePunchSets() {
        return jobTimePunchSets;
    }

    public void setJobTimePunchSets(ArrayList<JobTimePunchSet> jobTimePunchSets) {
        this.jobTimePunchSets = jobTimePunchSets;
    }

    public ArrayList<SaleLead> getLeads() {
        return leads;
    }

    public void setLeads(ArrayList<SaleLead> leads) {
        this.leads = leads;
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
