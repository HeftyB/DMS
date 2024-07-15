package com.heftyb.dms.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "accountTransactions")
public class AccountTransaction extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "transactionId", referencedColumnName = "id")
    private Transaction transaction;

    @NotNull
    private AccountTransactionType type;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private int amount;

    public AccountTransaction() {
    }

    public AccountTransaction(Account account, Transaction transaction, AccountTransactionType type, LocalDateTime date, int amount) {
        this.account = account;
        this.transaction = transaction;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public AccountTransactionType getType() {
        return type;
    }

    public void setType(AccountTransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
