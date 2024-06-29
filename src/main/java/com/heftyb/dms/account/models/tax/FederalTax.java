package com.heftyb.dms.account.models.tax;

import com.heftyb.dms.account.models.fee.FeeType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "federalTaxes")
public class FederalTax {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated
    private FeeType type = FeeType.PERCENTAGE;

    private double rate;

    private boolean isActive;

    @OneToMany(mappedBy = "federal", cascade = CascadeType.ALL)
    private List<TaxCharge> taxCharges = new ArrayList<>();

    public FederalTax() {
    }

    public FederalTax(double rate) {
        this.rate = rate;
    }

    public FederalTax(double rate, boolean isActive) {
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

    public List<TaxCharge> getTaxCharges() {
        return taxCharges;
    }

    public void setTaxCharges(List<TaxCharge> taxCharges) {
        this.taxCharges = taxCharges;
    }
}
