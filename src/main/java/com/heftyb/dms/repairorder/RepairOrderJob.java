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
    @JoinColumn(referencedColumnName = "id")
    private RepairOrder repairOrder;

    private String concern;

    private String cause;

    private String correction;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<Part> parts;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<TimePunch> timePunches;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private ArrayList<TechnicianFlatRateHour> labor;

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
        this.labor = new ArrayList<>();
    }

    public RepairOrderJob(RepairOrder repairOrder ,String concern, String cause, String correction) {
        this.repairOrder = repairOrder;
        this.concern = concern;
        this.cause = cause;
        this.correction = correction;
        this.parts = new ArrayList<>();
        this.timePunches = new ArrayList<>();
        this.labor = new ArrayList<>();
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

    public ArrayList<TimePunch> getTimePunches() {
        return timePunches;
    }

    public void setTimePunches(ArrayList<TimePunch> timePunches) {
        this.timePunches = timePunches;
    }

    public ArrayList<TechnicianFlatRateHour> getLabor() {
        return labor;
    }

    public void setLabor(ArrayList<TechnicianFlatRateHour> labor) {
        this.labor = labor;
    }

    public ArrayList<MiscellaneousItem> getMiscItems() {
        return miscItems;
    }

    public void setMiscItems(ArrayList<MiscellaneousItem> miscItems) {
        this.miscItems = miscItems;
    }

    public double getTotalPartsCost() {
        return parts.stream().mapToDouble(Part::getPrice).sum();
    }

    public double getTotalLaborCost() {
        return labor.stream().mapToDouble(TechnicianFlatRateHour::getFlatRateHours).sum() * Double.parseDouble(System.getenv("LABOR_RATE"));
    }
}
