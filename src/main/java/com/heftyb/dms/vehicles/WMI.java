package com.heftyb.dms.vehicles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "worldManufacturerIdentifiers")
public class WMI extends Auditable {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String wmi;

    @ManyToOne
    @JsonIgnore
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
