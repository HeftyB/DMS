package com.heftyb.dms.account.models.fee;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FeeChargeId implements Serializable {
    private long fee;
    private long charge;

    public FeeChargeId() {
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getCharge() {
        return charge;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeeChargeId)) return false;
        FeeChargeId that = (FeeChargeId) o;
        return getFee() == that.getFee() && getCharge() == that.getCharge();
    }

    @Override
    public int hashCode() {
        return 41;
    }
}
