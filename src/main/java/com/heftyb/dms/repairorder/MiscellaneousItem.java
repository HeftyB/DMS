package com.heftyb.dms.repairorder;

import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;

@Entity
@Table(name = "miscellaneousItem")
public class MiscellaneousItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String Description;
    private double cost;

    private BasicRepairOrder repairOrder;
    private PurchaseOrder purchaseOrder;
    private Vehicle vehicle;
}
