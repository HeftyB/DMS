package com.heftyb.dms.inventory;

import jakarta.persistence.*;

@Entity
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String source;
    private String partnumber;
    private String oempartnumber;

    private String description;

    private float cost;
    private float price;

    private float markup;

    public Part() {

    }

    public Part(String source, String partnumber, String oempartnumber, String description, float cost, float price, float markup) {
        this.source = source;
        this.partnumber = partnumber;
        this.oempartnumber = oempartnumber;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.markup = markup;
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

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public String getOempartnumber() {
        return oempartnumber;
    }

    public void setOempartnumber(String oempartnumber) {
        this.oempartnumber = oempartnumber;
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

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", partnumber='" + partnumber + '\'' +
                ", oempartnumber='" + oempartnumber + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", price=" + price +
                ", markup=" + markup +
                '}';
    }
}
