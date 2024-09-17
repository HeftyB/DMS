package com.heftyb.dms.inventory;

import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.WorkOrderJob;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "repairOrderJobParts")
@IdClass(WorkOrderJobPartId.class)
public class WorkOrderJobPart extends Auditable implements Serializable {

    @NotNull
    @Id
    @ManyToOne
    @JoinColumn()
    private WorkOrderJob job;

    @NotNull
    @Id
    @ManyToOne
    @JoinColumn()
    private Part part;


    private int quantity;
    private double unitPrice;

    public WorkOrderJobPart() {
    }

    public WorkOrderJob getJob() {
        return job;
    }

    public void setJob(WorkOrderJob job) {
        this.job = job;
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
        if (!(o instanceof WorkOrderJobPart)) return false;
        WorkOrderJobPart that = (WorkOrderJobPart) o;
        return getQuantity() == that.getQuantity()
                && Double.compare(that.getUnitPrice(), getUnitPrice()) == 0
                && getJob().equals(that.getJob())
                && getPart().equals(that.getPart());
    }

    @Override
    public int hashCode() {
        return 67;
    }
}
