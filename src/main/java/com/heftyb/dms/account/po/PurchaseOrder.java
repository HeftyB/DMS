package com.heftyb.dms.account.po;

import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.account.tax.TaxCharge;
import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.Vendor;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchaseOrders")
public class PurchaseOrder extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn
    private Vendor from;

    @ManyToOne
    @JoinColumn
    private Vendor to;

    @ManyToOne
    @JoinColumn
    private ContactInformation fromContact;

    @ManyToOne
    @JoinColumn
    private ContactInformation toContact;

    private String shippingMethod;

    private String paymentTerms;
    private String requiredByDate;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<POItem> items;

    private String notes;

    private double subTotal;

    @OneToOne(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private TaxCharge taxes;

    private double shipping;
    private double other;
    private double totalCost;

    @ManyToOne
    @JoinColumn
    private Employee approvedBy;

    public PurchaseOrder() {
    }

    public PurchaseOrder(long id, Vendor from, Vendor to, ContactInformation fromContact, ContactInformation toContact, String shippingMethod, String paymentTerms, String requiredByDate, ArrayList<POItem> items, String notes, double subTotal, TaxCharge taxes, double shipping, double other, double totalCost, Employee approvedBy) {
        this.id = id;
        this.date = Date.from(Instant.now());
        this.from = from;
        this.to = to;
        this.fromContact = fromContact;
        this.toContact = toContact;
        this.shippingMethod = shippingMethod;
        this.paymentTerms = paymentTerms;
        this.requiredByDate = requiredByDate;
        this.items = items;
        this.notes = notes;
        this.subTotal = subTotal;
        this.taxes = taxes;
        this.shipping = shipping;
        this.other = other;
        this.totalCost = totalCost;
        this.approvedBy = approvedBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vendor getFrom() {
        return from;
    }

    public void setFrom(Vendor from) {
        this.from = from;
    }

    public Vendor getTo() {
        return to;
    }

    public void setTo(Vendor to) {
        this.to = to;
    }

    public ContactInformation getFromContact() {
        return fromContact;
    }

    public void setFromContact(ContactInformation fromContact) {
        this.fromContact = fromContact;
    }

    public ContactInformation getToContact() {
        return toContact;
    }

    public void setToContact(ContactInformation toContact) {
        this.toContact = toContact;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getRequiredByDate() {
        return requiredByDate;
    }

    public void setRequiredByDate(String requiredByDate) {
        this.requiredByDate = requiredByDate;
    }

    public List<POItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<POItem> items) {
        this.items = items;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public TaxCharge getTaxes() {
        return taxes;
    }

    public void setTaxes(TaxCharge salesTax) {
        this.taxes = salesTax;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setItems(List<POItem> items) {
        this.items = items;
    }
}
