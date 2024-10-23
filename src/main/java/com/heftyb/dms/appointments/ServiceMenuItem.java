package com.heftyb.dms.appointments;

import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "serviceMenuItems")
public class ServiceMenuItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String item;

    private String price = "";
    private String description = "";
    private boolean isSpecial = false;
    private String imgUrl = "";

    // number of 15 minute time blocks
    // service requires
    private int blocks;

    public ServiceMenuItem() {
    }

    public ServiceMenuItem(String item, int blocks) {
        this.item = item;
        this.blocks = blocks;
    }

    public ServiceMenuItem(String item, String price, String description, boolean isSpecial, String imgUrl, int blocks) {
        this.item = item;
        this.price = price;
        this.description = description;
        this.isSpecial = isSpecial;
        this.blocks = blocks;
        this.imgUrl = imgUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ServiceMenuItem{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", isSpecial=" + isSpecial +
                ", blocks=" + blocks +
                '}';
    }
}
