package com.heftyb.dms.account.tax;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.fee.FeeType;
import com.heftyb.dms.account.po.PurchaseOrder;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.repairorder.RepairOrder;
import jakarta.persistence.*;

@Entity
@Table(name = "taxCharges")
public class TaxCharge extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private FeeType type = FeeType.PERCENTAGE;

    @OneToOne
    @JoinColumn(name = "repairOrder", referencedColumnName = "id")
    @JsonIgnore
    private RepairOrder repairOrder;

    @OneToOne
    @JoinColumn(name = "purchaseOrder", referencedColumnName = "id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;
//    private VehicleSale vehicleSale;

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

    public TaxCharge(RepairOrder repairOrder, FederalTax federal, StateTax state, LocalTax local) {
        this.repairOrder = repairOrder;
        this.federal = federal;
        this.state = state;
        this.local = local;
    }

    public TaxCharge(PurchaseOrder purchaseOrder, FederalTax federal, StateTax state, LocalTax local) {
        this.purchaseOrder = purchaseOrder;
        this.federal = federal;
        this.state = state;
        this.local = local;
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

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
