package com.heftyb.dms.inventory;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

import java.util.Date;

@Embeddable
public class ReceivedPart {

    @Temporal(TemporalType.DATE)
    private Date received;

    @ManyToOne
    @JoinColumn
    private Employee checkedInBY;

    private String shipper;
    private String shipmentId;
    private String supplierInvoice;
    private String source;


    private int quantity;
    private double unitCost;
    private String altBin;

    public ReceivedPart() {
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public Employee getCheckedInBY() {
        return checkedInBY;
    }

    public void setCheckedInBY(Employee checkedInBY) {
        this.checkedInBY = checkedInBY;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getSupplierInvoice() {
        return supplierInvoice;
    }

    public void setSupplierInvoice(String supplierInvoice) {
        this.supplierInvoice = supplierInvoice;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public String getAltBin() {
        return altBin;
    }

    public void setAltBin(String altBin) {
        this.altBin = altBin;
    }

    @Override
    public String toString() {
        return "ReceivedPart{" +
                "received=" + received +
                ", checkedInBY=" + checkedInBY +
                ", shipper='" + shipper + '\'' +
                ", shipmentId='" + shipmentId + '\'' +
                ", supplierInvoice='" + supplierInvoice + '\'' +
                ", source='" + source + '\'' +
                ", quantity=" + quantity +
                ", unitCost=" + unitCost +
                ", altBin='" + altBin + '\'' +
                '}';
    }
}
