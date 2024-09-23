package com.heftyb.dms.repairorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.inventory.WorkOrderJobPart;
import com.heftyb.dms.timekeeping.JobTimePunchSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workOrderJobs")
public class WorkOrderJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated
    private WorkOrderStatus status;

    @NotNull
    private String concern;

    private String cause = "";

    private String correction = "";

    @NotNull
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private RepairOrder repairOrder;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<WorkOrderJobPart> parts;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobTimePunchSet> timeClockPunchSets;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<TechnicianFlatRateHour> labor;

    @ElementCollection
    private List<MiscellaneousItem> miscItems;

    public WorkOrderJob() {
        parts = new ArrayList<>();
        timeClockPunchSets = new ArrayList<>();
        labor = new ArrayList<>();
        miscItems = new ArrayList<>();
        status = WorkOrderStatus.ENTERED;
    }

    public WorkOrderJob(String s) {
        concern = s;
        parts = new ArrayList<>();
        timeClockPunchSets = new ArrayList<>();
        labor = new ArrayList<>();
        miscItems = new ArrayList<>();
        status = WorkOrderStatus.ENTERED;
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

    public List<WorkOrderJobPart> getParts() {
        return parts;
    }

    public void setParts(ArrayList<WorkOrderJobPart> parts) {
        this.parts = parts;
    }

    public List<JobTimePunchSet> getTimeClockPunchSets() {
        return timeClockPunchSets;
    }

    public void setTimeClockPunchSets(ArrayList<JobTimePunchSet> timeClockPunchSets) {
        this.timeClockPunchSets = timeClockPunchSets;
    }

    public List<TechnicianFlatRateHour> getLabor() {
        return labor;
    }

    public void setLabor(ArrayList<TechnicianFlatRateHour> labor) {
        this.labor = labor;
    }

    public List<MiscellaneousItem> getMiscItems() {
        return miscItems;
    }

    public double getTotalPartsCost() {
        return parts.stream().mapToDouble(WorkOrderJobPart::getUnitPrice).sum();
    }

    public double getTotalLaborCost() {
        return labor.stream().mapToDouble(TechnicianFlatRateHour::getFlatRateHours).sum() * Double.parseDouble(System.getenv("LABOR_RATE"));
    }

    public void setParts(List<WorkOrderJobPart> parts) {
        this.parts = parts;
    }

    public void setTimeClockPunchSets(List<JobTimePunchSet> timeClockPunchSets) {
        this.timeClockPunchSets = timeClockPunchSets;
    }

    public void setLabor(List<TechnicianFlatRateHour> labor) {
        this.labor = labor;
    }

    public void setMiscItems(List<MiscellaneousItem> miscItems) {
        this.miscItems = miscItems;
    }

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RepairOrderJob{" +
                "id=" + id +
                ", concern='" + concern + '\'' +
                ", cause='" + cause + '\'' +
                ", correction='" + correction + '\'' +
                ", parts=" + parts +
                ", timeClockPunchSets=" + timeClockPunchSets +
                ", labor=" + labor +
                ", miscItems=" + miscItems +
                '}';
    }
}
