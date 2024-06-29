package com.heftyb.dms.account.models.fee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heftyb.dms.account.models.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "feeCharges")
@IdClass(FeeChargeId.class)
public class FeeCharge extends Auditable implements Serializable {
    @NotNull
    @Id
    @ManyToOne
    @JoinColumn(name = "feeId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = "charges", allowSetters = true)
    private Fee fee;

    @NotNull
    @Id
    @ManyToOne
    @JoinColumn(name = "chargeId", referencedColumnName = "id")
    private Charge charge;

    public FeeCharge() {
    }

    public FeeCharge(Fee fee, Charge charge) {
        this.fee = fee;
        this.charge = charge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeeCharge)) return false;
        FeeCharge feeCharge = (FeeCharge) o;
        return ((fee == null) ? 0 : fee.getId()) == ((feeCharge.fee == null) ? 0 : feeCharge.fee.getId()) &&
                ((charge == null) ? 0 : charge.getId()) == ((feeCharge.charge == null) ? 0 : feeCharge.charge.getId());
    }

    @Override
    public int hashCode() {
        return 29;
    }
}
