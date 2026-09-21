package com.heftyb.dms.account.statement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.invoice.Invoice;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "statements")
public class StatementItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Statement statement;

    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    private String invoiceNumber;
    private String description;
    private String poNumber;
    private double total;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    private StatementStatus status;

    @OneToMany(mappedBy = "statementItem")
    private List<InterestCharge> interestCharges;

//    private double financeFee;

    public StatementItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
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

    // TODO: calculate the finance charge per interval, from the days the invoice is past due and
    //  the invoice terms' status rates: the monthly rate under 180 days, the annual rate after.
}
