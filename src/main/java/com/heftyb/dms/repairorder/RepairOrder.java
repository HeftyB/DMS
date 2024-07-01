package com.heftyb.dms.repairorder;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.fee.Fee;
import com.heftyb.dms.account.models.fee.FeeCharge;
import com.heftyb.dms.account.models.tax.TaxCharge;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.inventory.Part;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "repairOrders")
public class RepairOrder extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private ZonedDateTime openDate;
    private ZonedDateTime closedDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "repairOrders")
    private Vehicle vehicle;

    private int mileageIn;
    private int mileageOut;
    private String serviceTag;

    @ManyToOne
    @JoinColumn(referencedColumnName = "")
    private Employee openedBy;
    private Employee technician;

    private boolean isCompleted;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<RepairOrderJob> jobs;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<Part> parts;

    private float subtotal;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<FeeCharge> fees;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<MiscellaneousItem> miscItems;

    @OneToOne
    @JoinColumn(name = "taxChargeId", referencedColumnName = "id")
    private TaxCharge taxes;

    private double totalAmount;

    public RepairOrder() {
    }

    public RepairOrder(ZonedDateTime openDate, ZonedDateTime closedDate, Customer customer, Vehicle vehicle, int mileageIn, int mileageOut, String serviceTag, Employee openedBy, ArrayList<RepairOrderJob> jobs) {
        this.openDate = openDate;
        this.closedDate = closedDate;
        this.customer = customer;
        this.vehicle = vehicle;
        this.mileageIn = mileageIn;
        this.mileageOut = mileageOut;
        this.serviceTag = serviceTag;
        this.openedBy = openedBy;
        this.jobs = jobs;
    }

}
