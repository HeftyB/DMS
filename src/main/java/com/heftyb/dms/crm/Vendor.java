package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.account.invoice.Invoice;
import com.heftyb.dms.account.po.PurchaseOrder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String companyName;
    private String personOfContact;

    @OneToOne(mappedBy = "vendor", cascade = CascadeType.ALL)
    private MailingAddress address;

    @OneToMany
    private List<PhoneNumber> phoneNumbers;

    private String email;

    private String taxId;
    private String paymentMethod;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PurchaseOrder> sentPurchaseOrders;

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PurchaseOrder> receivedPurchaseOrders;

    @OneToMany(mappedBy = "vendor")
    @JsonIgnore
    private List<Invoice> invoices;

    public Vendor() {
        phoneNumbers = new ArrayList<>();
        sentPurchaseOrders = new ArrayList<>();
        receivedPurchaseOrders = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    public Vendor(String companyName, String personOfContact, MailingAddress address, List<PhoneNumber> phoneNumbers, String email, String taxId, String paymentMethod) {
        this.companyName = companyName;
        this.personOfContact = personOfContact;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
        this.email = email;
        this.taxId = taxId;
        this.paymentMethod = paymentMethod;
        this.sentPurchaseOrders = new ArrayList<>();
        this.receivedPurchaseOrders = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPersonOfContact() {
        return personOfContact;
    }

    public void setPersonOfContact(String personOfContact) {
        this.personOfContact = personOfContact;
    }

    public MailingAddress getAddress() {
        return address;
    }

    public void setAddress(MailingAddress address) {
        this.address = address;
    }


    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phone) {
        this.phoneNumbers = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxid) {
        this.taxId = taxid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentmethod) {
        this.paymentMethod = paymentmethod;
    }

    public List<PurchaseOrder> getSentPurchaseOrders() {
        return sentPurchaseOrders;
    }

    public void setSentPurchaseOrders(ArrayList<PurchaseOrder> sentPurchaseOrders) {
        this.sentPurchaseOrders = sentPurchaseOrders;
    }

    public List<PurchaseOrder> getReceivedPurchaseOrders() {
        return receivedPurchaseOrders;
    }

    public void setReceivedPurchaseOrders(ArrayList<PurchaseOrder> receivedPurchaseOrders) {
        this.receivedPurchaseOrders = receivedPurchaseOrders;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
}
