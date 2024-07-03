package com.heftyb.dms.crm;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.po.PurchaseOrder;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "vendors")
public class Vendor extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;
    private String city;
    private String zipcode;
    private String phone;
    private String email;

    private String taxId;
    private String paymentMethod;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private ArrayList<PurchaseOrder> sentPurchaseOrders;

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    private ArrayList<PurchaseOrder> receivedPurchaseOrders;

    public Vendor() {
    }

    public Vendor(String address, String city, String zipcode, String phone, String email, String taxId, String paymentMethod) {
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.phone = phone;
        this.email = email;
        this.taxId = taxId;
        this.paymentMethod = paymentMethod;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public ArrayList<PurchaseOrder> getSentPurchaseOrders() {
        return sentPurchaseOrders;
    }

    public void setSentPurchaseOrders(ArrayList<PurchaseOrder> sentPurchaseOrders) {
        this.sentPurchaseOrders = sentPurchaseOrders;
    }

    public ArrayList<PurchaseOrder> getReceivedPurchaseOrders() {
        return receivedPurchaseOrders;
    }

    public void setReceivedPurchaseOrders(ArrayList<PurchaseOrder> receivedPurchaseOrders) {
        this.receivedPurchaseOrders = receivedPurchaseOrders;
    }
}
