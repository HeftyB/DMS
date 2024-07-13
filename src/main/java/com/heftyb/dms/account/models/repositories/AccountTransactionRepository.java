package com.heftyb.dms.account.models.repositories;

import com.heftyb.dms.account.models.AccountTransaction;
import org.springframework.data.repository.CrudRepository;

public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, Long> {
}
