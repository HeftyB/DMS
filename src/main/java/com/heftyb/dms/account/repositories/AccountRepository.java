package com.heftyb.dms.account.repositories;

import com.heftyb.dms.account.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
