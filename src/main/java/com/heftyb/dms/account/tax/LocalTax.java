package com.heftyb.dms.account.tax;

import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.account.fee.FeeType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "localTaxes")
public class LocalTax extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated
    private FeeType type = FeeType.PERCENTAGE;

    private double rate;

    private boolean isActive;

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
    private List<TaxCharge> taxCharge = new ArrayList<>();

    public LocalTax() {
    }

    public LocalTax(double rate) {
        this.rate = rate;
    }

    public LocalTax(double rate, boolean isActive) {
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
