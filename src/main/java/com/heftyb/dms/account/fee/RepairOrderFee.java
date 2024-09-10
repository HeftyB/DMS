package com.heftyb.dms.account.fee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
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
    private Fee fee;

    @NotNull
    @Id
    @ManyToOne
    @JoinColumn()
    @JsonIgnore
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
