package com.heftyb.dms.repairorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.account.fee.RepairOrderFee;
import com.heftyb.dms.account.invoice.Invoice;
import com.heftyb.dms.account.tax.TaxCharge;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "repairOrders")
public class RepairOrder extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date openDate;

    @Temporal(TemporalType.DATE)
    private Date finalizedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date closedDate;

    @OneToOne(mappedBy = "repairOrder")
    @JsonIgnore
    private Invoice invoice;


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

    @JsonIgnore
    private boolean isActive;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private List<RepairOrderJob> jobs;

    private float subtotal;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private List<RepairOrderFee> fees;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private List<MiscellaneousItem> miscItems;

    @OneToOne
    @JoinColumn(name = "taxChargeId")
    private TaxCharge taxes;

    private double totalAmount;

    public RepairOrder() {
    }

    public RepairOrder(Date openDate, Invoice invoice, Customer customer, Vehicle vehicle, int mileageIn, int mileageOut, String serviceTag, Employee advisor, ArrayList<RepairOrderJob> jobs) {
        this.openDate = openDate;
        this.invoice = invoice;
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

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<RepairOrderJob> getJobs() {
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

    public List<RepairOrderFee> getFees() {
        return fees;
    }

    public void setFees(ArrayList<RepairOrderFee> fees) {
        this.fees = fees;
    }

    public List<MiscellaneousItem> getMiscItems() {
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

    public Date getFinalizedDate() {
        return finalizedDate;
    }

    public void setFinalizedDate(Date finalizedDate) {
        this.finalizedDate = finalizedDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setJobs(List<RepairOrderJob> jobs) {
        this.jobs = jobs;
    }

    public void setFees(List<RepairOrderFee> fees) {
        this.fees = fees;
    }

    public void setMiscItems(List<MiscellaneousItem> miscItems) {
        this.miscItems = miscItems;
    }
}
