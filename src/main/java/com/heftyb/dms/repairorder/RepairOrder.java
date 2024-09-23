package com.heftyb.dms.repairorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.fee.RepairOrderFee;
import com.heftyb.dms.account.invoice.Invoice;
import com.heftyb.dms.account.tax.TaxCharge;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "repairOrders")
public class RepairOrder extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated
    private WorkOrderStatus status;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date openDate;


    @Temporal(TemporalType.DATE)
    private Date finalizedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date closedDate;

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "vehicleId")
    private Vehicle vehicle;

    @NotNull
    private int mileageIn;

    private int mileageOut;

    @NotNull
    private String serviceTag;

    @ManyToOne
    @JoinColumn()
    @NotNull
    private Employee advisor;

    @NotNull
    @JsonIgnore
    private boolean isActive;

    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "/([0-9])+/g")
    private String priority = "0";


    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private List<WorkOrderJob> jobs;

    private float subtotal;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private List<RepairOrderFee> fees;

    @ElementCollection
    private List<MiscellaneousItem> miscItems;

    @OneToOne
    @JoinColumn(name = "taxChargeId")
    private TaxCharge taxes;

    private double totalAmount;

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    public RepairOrder() {
        isActive = true;
        jobs = new ArrayList<>();
        fees = new ArrayList<>();
        miscItems = new ArrayList<>();
    }

    public RepairOrder(Vehicle vehicle, int mileageIn, String serviceTag, Employee advisor, String priority) {
        status = WorkOrderStatus.ENTERED;
        openDate = Date.from(Instant.now());
        customer = vehicle.getCustomer();

        this.vehicle = vehicle;
        this.mileageIn = mileageIn;
        this.serviceTag = serviceTag;
        this.advisor = advisor;
        this.priority = priority;
        this.jobs = new ArrayList<>();
        fees = new ArrayList<>();
        miscItems = new ArrayList<>();
        isActive = true;

    }

    public RepairOrder(Date openDate, Customer customer, Vehicle vehicle, int mileageIn, String serviceTag, Employee advisor, List<WorkOrderJob> jobs) {
        this.openDate = openDate;
        this.customer = customer;
        this.vehicle = vehicle;
        this.mileageIn = mileageIn;
        this.serviceTag = serviceTag;
        this.advisor = advisor;
        this.jobs = jobs;
        isActive = true;
    }

    public RepairOrder(Date openDate, Customer customer, Vehicle vehicle, int mileageIn, String serviceTag, Employee advisor) {
        this.openDate = openDate;
        this.customer = customer;
        this.vehicle = vehicle;
        this.mileageIn = mileageIn;
        this.serviceTag = serviceTag;
        this.advisor = advisor;
        this.jobs = new ArrayList<>();
        isActive = true;
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

    public List<WorkOrderJob> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<WorkOrderJob> jobs) {
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setJobs(List<WorkOrderJob> jobs) {
        this.jobs = jobs;
    }

    public void setFees(List<RepairOrderFee> fees) {
        this.fees = fees;
    }

    public void setMiscItems(List<MiscellaneousItem> miscItems) {
        this.miscItems = miscItems;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    public void addJob(WorkOrderJob workOrderJob) {
        this.jobs.add(workOrderJob);
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "id=" + id +
                ", status=" + status +
                ", openDate=" + openDate +
                ", finalizedDate=" + finalizedDate +
                ", closedDate=" + closedDate +
                ", customer=" + customer +
                ", vehicle=" + vehicle +
                ", mileageIn=" + mileageIn +
                ", mileageOut=" + mileageOut +
                ", serviceTag='" + serviceTag + '\'' +
                ", advisor=" + advisor +
                ", isActive=" + isActive +
                ", priority='" + priority + '\'' +
                ", jobs=" + jobs +
                ", subtotal=" + subtotal +
                ", fees=" + fees +
                ", miscItems=" + miscItems +
                ", taxes=" + taxes +
                ", totalAmount=" + totalAmount +
                ", invoice=" + invoice +
                '}';
    }
}
