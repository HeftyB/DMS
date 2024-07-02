package com.heftyb.dms.account.models.fee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.repairorder.RepairOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "repairOrderFees")
@IdClass(RepairOrderFeeId.class)
public class RepairOrderFee extends Auditable implements Serializable {
    @NotNull
    @Id
    @ManyToOne
    @JoinColumn(name = "feeId")
    @JsonIgnoreProperties(value = "charges", allowSetters = true)
    private Fee fee;

    @NotNull
    @Id
    @ManyToOne
    @JoinColumn()
    private RepairOrder repairOrder;

    public RepairOrderFee() {
    }

    public RepairOrderFee(Fee fee, RepairOrder repairOrder) {
        this.fee = fee;
        this.repairOrder = repairOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepairOrderFee)) return false;
        RepairOrderFee r = (RepairOrderFee) o;
        return ((fee == null) ? 0 : fee.getId()) == ((r.fee == null) ? 0 : r.fee.getId()) &&
                ((repairOrder == null) ? 0 : repairOrder.getId()) == ((r.repairOrder == null) ? 0 : r.repairOrder.getId());
    }

    @Override
    public int hashCode() {
        return 29;
    }
}
