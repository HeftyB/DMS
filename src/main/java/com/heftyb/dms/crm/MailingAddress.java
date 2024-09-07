package com.heftyb.dms.crm;

import jakarta.persistence.*;

@Entity
@Table(name = "mailingAddresses")
public class MailingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String addressLine1;
    private String addressLine2;

    private String city;
    private String state;
    private String zip;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Employee employee;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Vendor vendor;

    public MailingAddress() {
    }

    public MailingAddress(String name, String addressLine1, String addressLine2, String city, String state, String zip) {
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public MailingAddress(String addressLine1, String addressLine2, String city, String state, String zip, Customer customer) {
        this.name = customer.getFirstName() + " " + customer.getLastName();
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.customer = customer;
    }

    public MailingAddress(String addressLine1, String addressLine2, String city, String state, String zip, Employee employee) {
        this.name = employee.getPreferredName() + " " + employee.getLastName();
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.employee = employee;
    }

    public MailingAddress(String addressLine1, String addressLine2, String city, String state, String zip, Vendor vendor) {
        this.name = vendor.getCompanyName();
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.vendor = vendor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getAddress() {
        return String.format("%s \n %s \n %s \n %s, %s %s", name, addressLine1, addressLine2, city, state, zip);
    }

    @Override
    public String toString() {
        return "MailingAddress{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
