package com.heftyb.dms.inventory;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RepairOrderJobPartId implements Serializable {
    private long repairOrderJob;
    private long part;

    public RepairOrderJobPartId() {
    }

    public long getRepairOrderJob() {
        return repairOrderJob;
    }

    public void setRepairOrderJob(long repairOrderJob) {
        this.repairOrderJob = repairOrderJob;
    }

    public long getPart() {
        return part;
    }

    public void setPart(long part) {
        this.part = part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepairOrderJobPartId)) return false;
        RepairOrderJobPartId that = (RepairOrderJobPartId) o;
        return getRepairOrderJob() == that.getRepairOrderJob() && getPart() == that.getPart();
    }

    @Override
    public int hashCode() {
        return 61;
    }
}
