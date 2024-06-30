package com.heftyb.dms.repairorder;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class RepairOrderJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String concern;

    private String cause;

    private String correction;

    @OneToMany
    private ArrayList<Part> parts;

    @OneToMany
    private ArrayList<TimePunch> timePunches;

    private float labor;

    @OneToMany
    private ArrayList<MiscellaneousItems> miscitems;

    public RepairOrderJob() {
    }

    public RepairOrderJob(String concern) {
        this.concern = concern;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConcern() {
        return concern;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }

    public float getLabor() {
        return labor;
    }

    public void setLabor(float labor) {
        this.labor = labor;
    }

    public ArrayList<TimePunch> getTimePunches() {
        return timePunches;
    }

    public void setTimePunches(ArrayList<TimePunch> timePunches) {
        this.timePunches = timePunches;
    }
}
