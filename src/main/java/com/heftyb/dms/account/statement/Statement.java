package com.heftyb.dms.account.statement;

import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.Vendor;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "statements")
public class Statement extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;

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
    private List<StatementItem> statementItems;


}
