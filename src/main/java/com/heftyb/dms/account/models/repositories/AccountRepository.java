package com.heftyb.dms.account.models.repositories;

import com.heftyb.dms.account.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
