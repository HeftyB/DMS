package com.heftyb.dms.crm;

import jakarta.persistence.*;


@Embeddable
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;

    @Embedded
    private Zipcode zip;

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

    public Zipcode getZip() {
        return zip;
    }

    public void setZip(Zipcode zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return String.format("%s \n %s \n %s, %s %s", addressLine1, addressLine2, city, state, zip);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
