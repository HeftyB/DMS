package com.heftyb.dms.account.fee;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RepairOrderFeeId implements Serializable {
    private long fee;
    private long repairOrder;

    public RepairOrderFeeId() {
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(long repairOrder) {
        this.repairOrder = repairOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepairOrderFeeId)) return false;
        RepairOrderFeeId that = (RepairOrderFeeId) o;
        return getFee() == that.getFee() && getRepairOrder() == that.getRepairOrder();
    }

    @Override
    public int hashCode() {
        return 41;
    }
}
