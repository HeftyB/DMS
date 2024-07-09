package com.heftyb.dms.account.models.repositories;

import com.heftyb.dms.account.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
