package com.heftyb.dms.account.repositories;

import com.heftyb.dms.account.AccountTransaction;
import org.springframework.data.repository.CrudRepository;

public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, Long> {
}
