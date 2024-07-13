package com.heftyb.dms.account.models.invoice;


import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.Status;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Vendor;
import com.heftyb.dms.repairorder.RepairOrder;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "invoices")
public class Invoice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated
    private InvoiceType type;

    private String invoiceNumber;
    private String poNumber;
    private ZonedDateTime date;
    private double total;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Vendor vendor;

    private String terms;
    private String notes;

    @OneToOne
    @JoinColumn(name = "RO", referencedColumnName = "id")
    private RepairOrder repairOrder;

    @Enumerated
    private Status status = Status.OPEN;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private ArrayList<InvoiceItem> items;

    public Invoice() {
    }

    public Invoice(InvoiceType type, String invoiceNumber, String poNumber, ZonedDateTime date, double total, Customer customer, Vendor vendor, String terms, String notes, RepairOrder repairOrder, Status status, ArrayList<InvoiceItem> items) {
        this.type = type;
        this.invoiceNumber = invoiceNumber;
        this.poNumber = poNumber;
        this.date = date;
        this.total = total;
        this.customer = customer;
        this.vendor = vendor;
        this.terms = terms;
        this.notes = notes;
        this.repairOrder = repairOrder;
        this.status = status;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double amount) {
        this.total = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<InvoiceItem> items) {
        this.items = items;
    }
}
