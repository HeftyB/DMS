package com.heftyb.dms.account.invoice;


import com.heftyb.dms.account.PaymentTerm;
import com.heftyb.dms.account.InvoiceStatus;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.Vendor;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.repairorder.RepairOrder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String invoiceNumber;
    private String authorizingPONumber;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    private double total;


    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private PaymentTerm terms;

    private String notes;

    @Enumerated
    private InvoiceStatus invoiceStatus;

    @ElementCollection
    private List<InvoiceItem> items;

    public Invoice() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getAuthorizingPONumber() {
        return authorizingPONumber;
    }

    public void setAuthorizingPONumber(String authorizingPONumber) {
        this.authorizingPONumber = authorizingPONumber;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public PaymentTerm getTerms() {
        return terms;
    }

    public void setTerms(PaymentTerm terms) {
        this.terms = terms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", authorizingPONumber='" + authorizingPONumber + '\'' +
                ", date=" + date +
                ", employee=" + employee +
                ", total=" + total +
                ", terms=" + terms +
                ", notes='" + notes + '\'' +
                ", invoiceStatus=" + invoiceStatus +
                ", items=" + items +
                '}';
    }
}
