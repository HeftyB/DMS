package com.heftyb.dms.account.models.fee.repositories;

import com.heftyb.dms.account.models.fee.Fee;
import org.springframework.data.repository.CrudRepository;

public interface FeeRepository extends CrudRepository<Fee, Long> {
}
