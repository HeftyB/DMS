package com.heftyb.dms.crm;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstname;
    private String lastname;
    private String preferredname;

    private String address;
    private String city;
    private String state;
    private String zipcode;

    private String taxid;

    @OneToMany
    private ArrayList<TimePunch> timePunches;

    private boolean clockedin;
    private boolean jobinprogress;
}
