package com.heftyb.dms.repairorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.po.PurchaseOrder;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;

@Entity
@Table(name = "miscellaneousItem")
public class MiscellaneousItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String Description;
    private double cost;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private RepairOrder repairOrder;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private RepairOrderJob job;


    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private PurchaseOrder purchaseOrder;


    public MiscellaneousItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public RepairOrderJob getJob() {
        return job;
    }

    public void setJob(RepairOrderJob job) {
        this.job = job;
    }
}
