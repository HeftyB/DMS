package com.heftyb.dms.inventory;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.po.PurchaseOrder;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.RepairOrderJob;
import jakarta.persistence.*;

@Entity
@Table(name = "parts")
public class Part extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String source;
    private String partNumber;
    private String OEMPartNumber;

    private String description;

    private float cost;
    private float price;

    private float markup;

    @ManyToOne
    @JoinColumn(name = "roJobId", referencedColumnName = "id")
    private RepairOrderJob repairOrderJob;


    @ManyToOne
    @JoinColumn(name = "purchaseOrderId", referencedColumnName = "id")
    private PurchaseOrder purchaseOrder;

//    private CounterTicket counterTicket;

    public Part() {

    }


    public Part(String source, String partNumber, String OEMPartNumber, String description, float cost, float price, float markup, RepairOrderJob repairOrderJob) {
        this.source = source;
        this.partNumber = partNumber;
        this.OEMPartNumber = OEMPartNumber;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.markup = markup;
        this.repairOrderJob = repairOrderJob;
    }

    public Part(String source, String partNumber, String OEMPartNumber, String description, float cost, float price, float markup, PurchaseOrder purchaseOrder) {
        this.source = source;
        this.partNumber = partNumber;
        this.OEMPartNumber = OEMPartNumber;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.markup = markup;
        this.purchaseOrder = purchaseOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partnumber) {
        this.partNumber = partnumber;
    }

    public String getOEMPartNumber() {
        return OEMPartNumber;
    }

    public void setOEMPartNumber(String oempartnumber) {
        this.OEMPartNumber = oempartnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMarkup() {
        return markup;
    }

    public void setMarkup(float markup) {
        this.markup = markup;
    }

    public RepairOrderJob getRepairOrderJob() {
        return repairOrderJob;
    }

    public void setRepairOrderJob(RepairOrderJob repairOrderJob) {
        this.repairOrderJob = repairOrderJob;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public RepairOrder getRepairOrder() {
        return repairOrderJob.getRepairOrder();
    }
}
