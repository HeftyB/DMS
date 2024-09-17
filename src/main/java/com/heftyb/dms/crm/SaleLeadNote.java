package com.heftyb.dms.crm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Embeddable
public class SaleLeadNote {

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Employee employee;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @NotNull
    private String note;

    @ManyToOne
    @JoinColumn
    private SaleLead lead;

    public SaleLeadNote() {
    }

    public SaleLeadNote(Employee employee, String note, SaleLead lead) {
        this.employee = employee;
        this.note = note;
        this.lead = lead;
        this.date = Date.from(Instant.now());
    }

    public SaleLeadNote(Employee employee, Customer customer, String note, SaleLead lead) {
        this.employee = employee;
        this.customer = customer;
        this.note = note;
        this.lead = lead;
        this.date = Date.from(Instant.now());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SaleLead getLead() {
        return lead;
    }

    public void setLead(SaleLead lead) {
        this.lead = lead;
    }

    @Override
    public String toString() {
        return "SaleLeadNote{" +
                "date=" + date +
                ", employee=" + employee +
                ", customer=" + customer +
                ", note='" + note + '\'' +
                '}';
    }
}
