package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String firstName;

    @NotNull
    private String lastName;

    @Embedded
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Vehicle> vehicles;

//    @OneToMany(mappedBy = "customer")
//    @JsonIgnore
//    private List<Invoice> invoices;

//    @OneToMany(mappedBy = "customer")
//    @JsonIgnore
//    private ArrayList<Statement> statements;

//    private ArrayList<SaleLead> leads;


    public Customer() {
        vehicles = new ArrayList<>();
//        invoices = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, Address address, ContactInformation contactInformation, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactInformation = contactInformation;
        this.email = email;
        vehicles = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, Address address, ContactInformation contactInformation, String email, List<Vehicle> vehicles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactInformation = contactInformation;
        this.email = email;
        this.vehicles = vehicles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", contactInformation=" + contactInformation +
                ", email='" + email + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }
}
