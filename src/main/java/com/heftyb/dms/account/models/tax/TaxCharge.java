package com.heftyb.dms.account.models.tax;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.fee.FeeType;
import jakarta.persistence.*;

@Entity
@Table(name = "taxCharges")
public class TaxCharge extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private FeeType type = FeeType.PERCENTAGE;

    @OneToOne
    @JoinColumn(name = "chargeId", referencedColumnName = "id")
    private Charge charge;

    @ManyToOne
    @JoinColumn(name = "federalTaxes", referencedColumnName = "id")
    private FederalTax federal;

    @ManyToOne
    @JoinColumn(name = "stateTaxes", referencedColumnName = "id")
    private StateTax state;

    @ManyToOne
    @JoinColumn(name = "localTaxes", referencedColumnName = "id")
    private LocalTax local;

    private double totalTax;


    public TaxCharge() {
    }

    public TaxCharge(Charge charge, FederalTax federal, StateTax state, LocalTax local) {
        this.charge = charge;
        this.federal = federal;
        this.state = state;
        this.local = local;
    }

    public TaxCharge(Charge charge, FederalTax federal, StateTax state, LocalTax local, double totalTax) {
        this.charge = charge;
        this.federal = federal;
        this.state = state;
        this.local = local;
        this.totalTax = totalTax;
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

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public FederalTax getFederal() {
        return federal;
    }

    public void setFederal(FederalTax federal) {
        this.federal = federal;
    }

    public StateTax getState() {
        return state;
    }

    public void setState(StateTax state) {
        this.state = state;
    }

    public LocalTax getLocal() {
        return local;
    }

    public void setLocal(LocalTax local) {
        this.local = local;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }
}
