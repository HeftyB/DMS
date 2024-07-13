package com.heftyb.dms.account.models.statement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.account.models.invoice.Invoice;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "statements")
public class StatementItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Statement statement;

    private ZonedDateTime invoiceDate;
    private String invoiceNumber;
    private String description;
    private String poNumber;
    private double total;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Invoice invoice;

    @Enumerated
    private StatementStatus status;

    private double financeFee;

    public StatementItem() {
    }

    public StatementItem(Statement statement, Invoice invoice, StatementStatus status) {
        this.statement = statement;
        this.invoice = invoice;
        this.invoiceNumber = invoice.getInvoiceNumber();
        this.description = invoice.getStatus().toString();
        this.poNumber = invoice.getPoNumber();
        this.total = invoice.getTotal();
        this.status = status;
        this.financeFee = calculateFinanceFee();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(ZonedDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public StatementStatus getStatus() {
        return status;
    }

    public void setStatus(StatementStatus status) {
        this.status = status;
    }
}
