package com.heftyb.dms.crm;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "saleLeads")
public class SaleLead {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Embedded
    private ContactInformation contactInfo;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    private String message;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ElementCollection
//    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL)
//    @Embedded
    private List<SaleLeadNote> saleLeadNotes;

    public SaleLead() {
    }

    public SaleLead(ContactInformation contactInfo, Employee employee, String message, Date date) {
        this.contactInfo = contactInfo;
        this.employee = employee;
        this.message = message;
        this.date = date;
        this.saleLeadNotes = new ArrayList<>();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SaleLeadNote> getNotes() {
        return saleLeadNotes;
    }

    public void setNotes(List<SaleLeadNote> saleLeadNotes) {
        this.saleLeadNotes = saleLeadNotes;
    }

    @Override
    public String toString() {
        return "SaleLead{" +
                "id=" + id +
                ", contactInfo=" + contactInfo +
                ", employee=" + employee +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", saleLeadNotes=" + saleLeadNotes +
                '}';
    }
}
