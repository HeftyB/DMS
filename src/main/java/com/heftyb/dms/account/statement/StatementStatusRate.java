package com.heftyb.dms.account.statement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.account.PaymentTerm;
import jakarta.persistence.*;

@Entity
@Table(name = "statementStatusRates")
public class StatementStatusRate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private StatementRateType status;

    private double rate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private PaymentTerm paymentTerm;

    public StatementStatusRate() {
    }

    public StatementStatusRate(StatementRateType status, double rate, PaymentTerm paymentTerm) {
        this.status = status;
        this.rate = rate;
        this.paymentTerm = paymentTerm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StatementRateType getStatus() {
        return status;
    }

    public void setStatus(StatementRateType status) {
        this.status = status;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }
}
