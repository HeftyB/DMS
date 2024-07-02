package com.heftyb.dms.vehicles;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class WMI {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String wmi;

    @ManyToOne
    private Manufacturer manufacturer;

    public WMI() {
    }

    public WMI(String name, String wmi, Manufacturer manufacturer) {
        this.name = name;
        this.wmi = wmi;
        this.manufacturer = manufacturer;
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

    public String getWmi() {
        return wmi;
    }

    public void setWmi(String wmi) {
        this.wmi = wmi;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
