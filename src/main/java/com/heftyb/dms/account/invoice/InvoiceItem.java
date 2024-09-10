package com.heftyb.dms.account.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "invoiceItems")
public class InvoiceItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String description;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Invoice invoice;


    private double quantity;
    private double rate;
    private double total;


    public InvoiceItem() {
    }

    public InvoiceItem(String description, Invoice invoice, double quantity, double rate, double total) {
        this.description = description;
        this.invoice = invoice;
        this.quantity = quantity;
        this.rate = rate;
        this.total = total;
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", invoice=" + invoice +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", total=" + total +
                '}';
    }
}
