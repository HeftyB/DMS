package com.heftyb.dms.repairorder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MiscellaneousItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String Description;

}
