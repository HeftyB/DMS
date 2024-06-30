package com.heftyb.dms.repairorder;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.inventory.Part;
import com.heftyb.dms.timekeeping.TimePunch;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "repairOrderJobs")
public class RepairOrderJob extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "repairOrderId", referencedColumnName = "id")
    private RepairOrder repairOrder;

    private String concern;

    private String cause;

    private String correction;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<Part> parts;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<TimePunch> timePunches;

    private float labor;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<MiscellaneousItem> miscItems;

    public RepairOrderJob() {
    }

    public RepairOrderJob(RepairOrder repairOrder ,String concern) {
        this.repairOrder = repairOrder;
        this.concern = concern;
        this.cause = "";
        this.correction = "";
        this.parts = new ArrayList<>();
        this.timePunches = new ArrayList<>();
    }

    public RepairOrderJob(RepairOrder repairOrder ,String concern, String cause, String correction) {
        this.repairOrder = repairOrder;
        this.concern = concern;
        this.cause = cause;
        this.correction = correction;
        this.parts = new ArrayList<>();
        this.timePunches = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public ArrayList<MiscellaneousItem> getMiscItems() {
        return miscItems;
    }

    public void setMiscItems(ArrayList<MiscellaneousItem> miscItems) {
        this.miscItems = miscItems;
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
