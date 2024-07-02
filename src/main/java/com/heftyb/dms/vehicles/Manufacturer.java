package com.heftyb.dms.vehicles;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private ArrayList<WMI> wmis;

    @OneToMany(mappedBy = "manufacturer")
    private ArrayList<Model> models;


}
