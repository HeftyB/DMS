package com.heftyb.dms.appointments;

import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "serviceMenuItems")
public class ServiceMenuItem extends Auditable {

    @Id
    @GeneratedValue
    private long id;

    private String item;

    // number of 15 minute time blocks
    // service requires
    private int blocks;

    public ServiceMenuItem() {
    }

    public ServiceMenuItem(String item, int blocks) {
        this.item = item;
        this.blocks = blocks;
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

    @Override
    public String toString() {
        return "ServiceMenuItem{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", blocks=" + blocks +
                '}';
    }
}
