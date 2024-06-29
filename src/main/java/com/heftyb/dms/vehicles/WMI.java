package com.heftyb.dms.vehicles;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class WMI {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String wmi;
}
