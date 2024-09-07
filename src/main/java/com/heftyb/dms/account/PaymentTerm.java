package com.heftyb.dms.account;

import com.heftyb.dms.account.statement.StatementStatusRate;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "paymentTerms")
public class PaymentTerm extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String terms;


    @OneToMany(mappedBy = "paymentTerm")
    private List<StatementStatusRate> statusRates;


    public PaymentTerm() {
    }

    public PaymentTerm(String terms, List<StatementStatusRate> statusRates) {
        this.terms = terms;
        this.statusRates = statusRates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public List<StatementStatusRate> getStatusRates() {
        return statusRates;
    }

    public void setStatusRates(List<StatementStatusRate> statusRates) {
        this.statusRates = statusRates;
    }
}
