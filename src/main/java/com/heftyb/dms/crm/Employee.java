package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

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

    @Embedded
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String taxId;
//
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<TimeClockPunchSet> timeClockPunchSets;
//
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<JobTimePunchSet> jobTimePunchSets;

    private boolean clockedIn;
    private boolean jobInProgress;

    private JobTitle jobTitle;

    private Date hiredDate;


//    private Employee manager;

//    private ArrayList<TimeSheet> timeSheets;
//    private ArrayList<PayCheck> payChecks;

//    @OneToMany(mappedBy = "employee")
//    private List<SaleLead> leads;

//    private ArrayList<CounterTicket> counterTickets;
//    private ArrayList<VehicleSale> vehicleSales;

//    @OneToMany(mappedBy = "advisor", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<RepairOrder> repairOrders;

//    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<TechnicianFlatRateHour> flatRateHours;


    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private User user;


    public Employee() {
//        timeClockPunchSets = new ArrayList<>();
//        jobTimePunchSets = new ArrayList<>();
//        repairOrders = new ArrayList<>();
//        flatRateHours = new ArrayList<>();
        clockedIn = false;
        jobInProgress = false;
    }

    public Employee(String firstName, String lastName, String preferredName, Address address, ContactInformation contactInformation, String taxId, JobTitle jobTitle, Date hiredDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.address = address;
        this.contactInformation = contactInformation;
        this.taxId = taxId;
        this.jobTitle = jobTitle;
        this.hiredDate = hiredDate;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
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

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", preferredName='" + preferredName + '\'' +
                ", address=" + address +
                ", contactInformation=" + contactInformation +
                ", taxId='" + taxId + '\'' +
                ", clockedIn=" + clockedIn +
                ", jobInProgress=" + jobInProgress +
                ", jobTitle=" + jobTitle +
                ", hiredDate=" + hiredDate +
                ", user=" + user +
                '}';
    }
}
