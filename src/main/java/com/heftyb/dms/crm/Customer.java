package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.account.invoice.Invoice;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String preferredName;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private MailingAddress mailingAddress;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;


    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Invoice> invoices;

//    @OneToMany(mappedBy = "customer")
//    @JsonIgnore
//    private ArrayList<Statement> statements;

//    private ArrayList<SaleLead> leads;


    public Customer() {}

    public Customer(String firstName, String lastName, String preferredName, MailingAddress mailingAddress, ArrayList<PhoneNumber> phoneNumbers, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.mailingAddress = mailingAddress;
        this.phoneNumbers = phoneNumbers;
        this.email = email;
        this.vehicles = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String preferredName, MailingAddress mailingAddress, ArrayList<PhoneNumber> phoneNumbers, String email, ArrayList<Vehicle> vehicles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.mailingAddress = mailingAddress;
        this.phoneNumbers = phoneNumbers;
        this.email = email;
        this.vehicles = vehicles;
        this.invoices = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String preferredName, ArrayList<PhoneNumber> phoneNumbers, ArrayList<Vehicle> vehicles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.phoneNumbers = phoneNumbers;
        this.vehicles = vehicles;
        this.invoices = new ArrayList<>();
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

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
}
