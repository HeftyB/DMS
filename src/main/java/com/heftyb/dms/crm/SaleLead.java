package com.heftyb.dms.crm;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "saleLeads")
public class SaleLead {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private ContactInformation contactInfo;
    private Employee employee;

    private String message;
    private ZonedDateTime date;

    private ArrayList<Note> notes;

    public SaleLead() {
    }

    public SaleLead(ContactInformation contactInfo, Employee employee, String message, ZonedDateTime date) {
        this.contactInfo = contactInfo;
        this.employee = employee;
        this.message = message;
        this.date = date;
        this.notes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ContactInformation getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInformation contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
}
