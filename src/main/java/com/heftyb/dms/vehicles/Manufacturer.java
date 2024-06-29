package com.heftyb.dms.vehicles;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;

public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private ArrayList<WMI> wmis;

    private ArrayList<Model> models;

}
