package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.models.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "phoneNumbers")
public class PhoneNumber extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String number;
    private boolean isPrimary;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private SaleLead lead;

    public PhoneNumber() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public SaleLead getLead() {
        return lead;
    }

    public void setLead(SaleLead lead) {
        this.lead = lead;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
