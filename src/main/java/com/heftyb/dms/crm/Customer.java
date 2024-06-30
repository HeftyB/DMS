package com.heftyb.dms.crm;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstname;
    private String lastname;
    private String nickname;

    private String addressline1;
    private String addressline2;
    private String city;
    private String state;
    private String zipcode;

    private String homephone;
    private String mobilephone;

    private String email;

    @OneToMany
    private ArrayList<Vehicle> vehicles;


    public Customer() {

    }

    public Customer(String firstname, String lastname, String nickname,
                    String addressline1, String addressline2,
                    String city, String state, String zipcode,
                    String homephone, String mobilephone, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.homephone = homephone;
        this.mobilephone = mobilephone;
        this.email = email;
    }
}
