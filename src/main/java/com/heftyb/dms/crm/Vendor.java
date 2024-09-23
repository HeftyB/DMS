package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.po.PurchaseOrder;
import com.heftyb.dms.dao.Auditable;
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

    @Embedded
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String email;

    private String taxId;
    private String paymentMethod;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PurchaseOrder> sentPurchaseOrders;

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PurchaseOrder> receivedPurchaseOrders;


    public Vendor() {
        sentPurchaseOrders = new ArrayList<>();
        receivedPurchaseOrders = new ArrayList<>();
    }

    public Vendor(String companyName, Address address, ContactInformation contactInformation, String email, String taxId, String paymentMethod) {
        this.companyName = companyName;
        this.address = address;
        this.contactInformation = contactInformation;
        this.email = email;
        this.taxId = taxId;
        this.paymentMethod = paymentMethod;
        this.sentPurchaseOrders = new ArrayList<>();
        this.receivedPurchaseOrders = new ArrayList<>();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
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

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<PurchaseOrder> getSentPurchaseOrders() {
        return sentPurchaseOrders;
    }

    public void setSentPurchaseOrders(List<PurchaseOrder> sentPurchaseOrders) {
        this.sentPurchaseOrders = sentPurchaseOrders;
    }

    public List<PurchaseOrder> getReceivedPurchaseOrders() {
        return receivedPurchaseOrders;
    }

    public void setReceivedPurchaseOrders(List<PurchaseOrder> receivedPurchaseOrders) {
        this.receivedPurchaseOrders = receivedPurchaseOrders;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", address=" + address +
                ", contactInformation=" + contactInformation +
                ", email='" + email + '\'' +
                ", taxId='" + taxId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", sentPurchaseOrders=" + sentPurchaseOrders +
                ", receivedPurchaseOrders=" + receivedPurchaseOrders +
                '}';
    }
}
