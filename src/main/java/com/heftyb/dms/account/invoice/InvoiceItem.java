package com.heftyb.dms.account.invoice;

import jakarta.persistence.Embeddable;

@Embeddable
public class InvoiceItem {

    private String description;


    private double quantity;
    private double rate;
    private double total;

    private String internalReferenceId;

    public InvoiceItem() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getInternalReferenceId() {
        return internalReferenceId;
    }

    public void setInternalReferenceId(String internalReferenceId) {
        this.internalReferenceId = internalReferenceId;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "description='" + description + '\'' +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", total=" + total +
                ", internalReferenceId=" + internalReferenceId +
                '}';
    }
}
