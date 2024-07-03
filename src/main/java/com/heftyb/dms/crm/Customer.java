package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "customers")
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private String preferredName;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;

    private String phone;
    private String phone1;
    private String phone2;

    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private ArrayList<Vehicle> vehicles;

//    private ArrayList<SaleLead> leads;


    public Customer() {}

    public Customer(String firstName, String lastName, String preferredName, String addressLine1, String addressLine2, String city, String state, String zipcode, String phone, String phone1, String phone2, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phone;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.vehicles = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.vehicles = new ArrayList<>();
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
