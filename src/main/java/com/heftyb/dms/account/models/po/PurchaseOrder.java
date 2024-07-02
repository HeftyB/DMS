package com.heftyb.dms.account.models.po;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.PaymentMethod;
import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.Vendor;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "purchaseOrders")
public class PurchaseOrder extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Vendor from;
    private Vendor to;

    private ContactInformation fromContact;
    private ContactInformation toContact;

    private String shippingMethod;

    private String paymentTerms;
    private String requiredByDate;

    @OneToMany(mappedBy = "purchaseOrder")
    private ArrayList<POItem> items;

    private String notes;

    private double subTotal;
    private double salesTax;
    private double shipping;
    private double other;
    private double totalCost;

    private Employee approvedBy;

    public PurchaseOrder() {
    }

    public PurchaseOrder(long id, Vendor from, Vendor to, ContactInformation fromContact, ContactInformation toContact, String shippingMethod, String paymentTerms, String requiredByDate, ArrayList<POItem> items, String notes, double subTotal, double salesTax, double shipping, double other, double totalCost, Employee approvedBy) {
        this.id = id;
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
        this.salesTax = salesTax;
        this.shipping = shipping;
        this.other = other;
        this.totalCost = totalCost;
        this.approvedBy = approvedBy;
    }
}
