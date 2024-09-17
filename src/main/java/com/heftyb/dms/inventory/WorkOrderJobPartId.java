package com.heftyb.dms.inventory;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class WorkOrderJobPartId implements Serializable {
    private long job;
    private long part;

    public WorkOrderJobPartId() {
    }

    public long getJob() {
        return job;
    }

    public void setJob(long job) {
        this.job = job;
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
        if (!(o instanceof WorkOrderJobPartId)) return false;
        WorkOrderJobPartId that = (WorkOrderJobPartId) o;
        return getJob() == that.getJob() && getPart() == that.getPart();
    }

    @Override
    public int hashCode() {
        return 61;
    }
}
