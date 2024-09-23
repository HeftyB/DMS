package com.heftyb.dms.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

import java.util.List;

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

    private String bin;
    private boolean inStock;
    private int qty;
    private float cost;
    private float markup;
    private float price;


    //    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL)
    @ElementCollection
    private List<ReceivedPart> receivedStock;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<WorkOrderJobPart> jobs;

//    private CounterTicket counterTicket;

    public Part() {

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

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getOEMPartNumber() {
        return OEMPartNumber;
    }

    public void setOEMPartNumber(String OEMPartNumber) {
        this.OEMPartNumber = OEMPartNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getMarkup() {
        return markup;
    }

    public void setMarkup(float markup) {
        this.markup = markup;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<ReceivedPart> getReceivedStock() {
        return receivedStock;
    }

    public void setReceivedStock(List<ReceivedPart> receivedStock) {
        this.receivedStock = receivedStock;
    }

    public List<WorkOrderJobPart> getJobs() {
        return jobs;
    }

    public void setJobs(List<WorkOrderJobPart> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", OEMPartNumber='" + OEMPartNumber + '\'' +
                ", description='" + description + '\'' +
                ", bin='" + bin + '\'' +
                ", inStock=" + inStock +
                ", qty=" + qty +
                ", cost=" + cost +
                ", markup=" + markup +
                ", price=" + price +
                ", receivedStock=" + receivedStock +
                ", jobs=" + jobs +
                '}';
    }
}
