package com.heftyb.dms.inventory;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.repairorder.RepairOrderJob;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "repairOrderJobParts")
@IdClass(RepairOrderJobPartId.class)
public class RepairOrderJobPart extends Auditable implements Serializable {

    @NotNull
    @Id
    @ManyToOne
    @JoinColumn()
    private RepairOrderJob repairOrderJob;

    @NotNull
    @Id
    @ManyToOne
    @JoinColumn()
    private Part part;


    private int quantity;
    private double unitPrice;

    public RepairOrderJobPart() {
    }

    public RepairOrderJobPart(RepairOrderJob repairOrderJob, Part part, int quantity, double unitPrice) {
        this.repairOrderJob = repairOrderJob;
        this.part = part;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public RepairOrderJob getRepairOrderJob() {
        return repairOrderJob;
    }

    public void setRepairOrderJob(RepairOrderJob repairOrderJob) {
        this.repairOrderJob = repairOrderJob;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepairOrderJobPart)) return false;
        RepairOrderJobPart that = (RepairOrderJobPart) o;
        return getQuantity() == that.getQuantity() && Double.compare(that.getUnitPrice(), getUnitPrice()) == 0 && getRepairOrderJob().equals(that.getRepairOrderJob()) && getPart().equals(that.getPart());
    }

    @Override
    public int hashCode() {
        return 67;
    }
}
