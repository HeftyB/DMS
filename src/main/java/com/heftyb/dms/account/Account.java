package com.heftyb.dms.account;

import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;
    private String description;

    @Enumerated
    @Column(name = "accountType")
    private AccountType accountType;

    @NotNull
    private String accountNumber;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountTransaction> transactions = new ArrayList<>();

}
