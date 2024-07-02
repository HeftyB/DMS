package com.heftyb.dms.crm;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private ZonedDateTime date;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    private String note;

    private SaleLead lead;

    public Note() {
    }

    public Note(Employee employee, Customer customer, String note, SaleLead lead) {
        this.employee = employee;
        this.customer = customer;
        this.note = note;
        this.lead = lead;
        this.date = ZonedDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
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
}
