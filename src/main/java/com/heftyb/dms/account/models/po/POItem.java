package com.heftyb.dms.account.models.po;

import com.heftyb.dms.account.models.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "PurchaseOrderItems")
public class POItem  extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String description;
    private String qty;
    private String unit1;
    private String unit2;
    private double totalCost;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    public POItem() {
    }

    public POItem(long id, String description, String qty, String unit1, String unit2, double totalCost, PurchaseOrder purchaseOrder) {
        this.id = id;
        this.description = description;
        this.qty = qty;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.totalCost = totalCost;
        this.purchaseOrder = purchaseOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
