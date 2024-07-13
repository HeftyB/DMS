package com.heftyb.dms.account.models.statement;

import com.heftyb.dms.account.models.Auditable;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Vendor;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "statements")
public class Statement extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private ZonedDateTime date;
    private double amount;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Vendor vendor;

    private String terms;
    private String notes;

    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL)
    private ArrayList<StatementItem> statementItems;


}
