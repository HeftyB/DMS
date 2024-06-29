package com.heftyb.dms.account.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "payments")
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Customer customer;

    @NotNull
    private String receipt;

    @NotNull
    private double total;

    @Enumerated
    private PaymentMethod paymentMethod;

    public Payment() {
    }

    public Payment(Customer customer, String receipt, double total) {
        this.customer = customer;
        this.receipt = receipt;
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", customer=" + customer +
                ", receipt='" + receipt + '\'' +
                ", total=" + total +
                '}';
    }
}
