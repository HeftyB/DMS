package com.heftyb.dms.inventory;

import com.heftyb.dms.account.Auditable;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "stockedParts")
public class StockedPart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private Part part;

    private ZonedDateTime received;

    private String invoice;
    private String source;
    private int quantity;
    private double unitCost;
    private String altBin;

    public StockedPart() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public ZonedDateTime getReceived() {
        return received;
    }

    public void setReceived(ZonedDateTime received) {
        this.received = received;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
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
}
