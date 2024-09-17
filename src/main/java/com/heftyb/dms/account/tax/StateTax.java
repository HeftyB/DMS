package com.heftyb.dms.account.tax;

import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.account.fee.FeeType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stateTaxes")
public class StateTax extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated
    private FeeType type = FeeType.PERCENTAGE;

    private double rate;

    private boolean isActive;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<TaxCharge> taxCharge = new ArrayList<>();

    public StateTax() {
    }

    public StateTax(double rate) {
        this.rate = rate;
    }

    public StateTax(double rate, boolean isActive) {
        this.rate = rate;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FeeType getType() {
        return type;
    }

    public void setType(FeeType type) {
        this.type = type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<TaxCharge> getTaxCharge() {
        return taxCharge;
    }

    public void setTaxCharge(List<TaxCharge> taxCharge) {
        this.taxCharge = taxCharge;
    }
}
