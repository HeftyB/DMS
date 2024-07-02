package com.heftyb.dms.vehicles;

import com.heftyb.dms.account.models.Auditable;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "manufacturers")
public class Manufacturer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private ArrayList<WMI> wmis;

    @OneToMany(mappedBy = "manufacturer")
    private ArrayList<Model> models;


}
