package com.heftyb.dms.crm;

import com.heftyb.dms.timekeeping.TimePunch;
import com.heftyb.dms.timekeeping.TimeSheet;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "employees")
public class Employee {

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

    @OneToMany
    private ArrayList<TimePunch> timePunches;

    private boolean clockedIn;
    private boolean jobInProgress;

    private JobTitle jobTitle;
    private Employee manager;

    private ArrayList<TimeSheet> timeSheets;
//    private ArrayList<PayCheck> payChecks;

//    private ArrayList<SaleLead> leads;


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
        this.manager = manager;
        this.timePunches = new ArrayList<>();
        this.timeSheets = new ArrayList<>();
//        this.payChecks = new ArrayList<>();
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

    public ArrayList<TimePunch> getTimePunches() {
        return timePunches;
    }

    public void setTimePunches(ArrayList<TimePunch> timePunches) {
        this.timePunches = timePunches;
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

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public ArrayList<TimeSheet> getTimeSheets() {
        return timeSheets;
    }

    public void setTimeSheets(ArrayList<TimeSheet> timeSheets) {
        this.timeSheets = timeSheets;
    }
}
