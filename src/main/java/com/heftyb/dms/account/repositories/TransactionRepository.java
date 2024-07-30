package com.heftyb.dms.account.repositories;

import com.heftyb.dms.account.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
