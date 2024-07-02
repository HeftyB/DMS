package com.heftyb.dms.repairorder;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.fee.Fee;
import com.heftyb.dms.account.models.fee.RepairOrderFee;
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
    @JoinColumn(name = "vehicleId")
    private Vehicle vehicle;

    private int mileageIn;
    private int mileageOut;
    private String serviceTag;

    @ManyToOne
    @JoinColumn()
    private Employee advisor;

    private boolean isCompleted;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<RepairOrderJob> jobs;

    private float subtotal;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<RepairOrderFee> fees;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private ArrayList<MiscellaneousItem> miscItems;

    @OneToOne
    @JoinColumn(name = "taxChargeId")
    private TaxCharge taxes;

    private double totalAmount;

    public RepairOrder() {
    }

    public RepairOrder(ZonedDateTime openDate, ZonedDateTime closedDate, Customer customer, Vehicle vehicle, int mileageIn, int mileageOut, String serviceTag, Employee advisor, ArrayList<RepairOrderJob> jobs) {
        this.openDate = openDate;
        this.closedDate = closedDate;
        this.customer = customer;
        this.vehicle = vehicle;
        this.mileageIn = mileageIn;
        this.mileageOut = mileageOut;
        this.serviceTag = serviceTag;
        this.advisor = advisor;
        this.jobs = jobs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(ZonedDateTime openDate) {
        this.openDate = openDate;
    }

    public ZonedDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(ZonedDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getMileageIn() {
        return mileageIn;
    }

    public void setMileageIn(int mileageIn) {
        this.mileageIn = mileageIn;
    }

    public int getMileageOut() {
        return mileageOut;
    }

    public void setMileageOut(int mileageOut) {
        this.mileageOut = mileageOut;
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public Employee getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Employee advisor) {
        this.advisor = advisor;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public ArrayList<RepairOrderJob> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<RepairOrderJob> jobs) {
        this.jobs = jobs;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public ArrayList<RepairOrderFee> getFees() {
        return fees;
    }

    public void setFees(ArrayList<RepairOrderFee> fees) {
        this.fees = fees;
    }

    public ArrayList<MiscellaneousItem> getMiscItems() {
        return miscItems;
    }

    public void setMiscItems(ArrayList<MiscellaneousItem> miscItems) {
        this.miscItems = miscItems;
    }

    public TaxCharge getTaxes() {
        return taxes;
    }

    public void setTaxes(TaxCharge taxes) {
        this.taxes = taxes;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
