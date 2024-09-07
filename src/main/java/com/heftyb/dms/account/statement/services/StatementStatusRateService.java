package com.heftyb.dms.account.statement.services;

import com.heftyb.dms.account.statement.StatementStatusRate;

import java.util.List;

public interface StatementStatusRateService {
    List<StatementStatusRate> findAll();

    StatementStatusRate findById(long id);

    StatementStatusRate save(StatementStatusRate statementStatusRate);

    void delete(long id);
}
