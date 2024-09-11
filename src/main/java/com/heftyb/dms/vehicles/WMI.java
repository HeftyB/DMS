package com.heftyb.dms.vehicles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

@Embeddable
public class WMI {

    private String name;
    private String wmi;

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

    @Override
    public String toString() {
        return "WMI{" +
                "name='" + name + '\'' +
                ", wmi='" + wmi + '\'' +
                '}';
    }
}
