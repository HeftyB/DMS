package com.heftyb.dms.account.statement;

import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "interestCharges")
public class InterestCharge extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private StatementItem statementItem;

    private double previousBal;
    private double endingBal;
    private double interestAmount;

    public InterestCharge() {
    }

    public InterestCharge(StatementItem statementItem, double previousBal, double endingBal, double interestAmount) {
        this.statementItem = statementItem;
        this.previousBal = previousBal;
        this.endingBal = endingBal;
        this.interestAmount = interestAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StatementItem getStatementItem() {
        return statementItem;
    }

    public void setStatementItem(StatementItem statementItem) {
        this.statementItem = statementItem;
    }

    public double getPreviousBal() {
        return previousBal;
    }

    public void setPreviousBal(double previousBal) {
        this.previousBal = previousBal;
    }

    public double getEndingBal() {
        return endingBal;
    }

    public void setEndingBal(double endingBal) {
        this.endingBal = endingBal;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
    }

}
