package com.heftyb.dms.repairorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.account.po.PurchaseOrder;
import jakarta.persistence.*;

@Embeddable
public class MiscellaneousItem extends Auditable {

    private String Description;
    private double cost;


    public MiscellaneousItem() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
