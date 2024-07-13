package com.heftyb.dms.inventory.repositories;

import com.heftyb.dms.inventory.StockedPart;
import org.springframework.data.repository.CrudRepository;

public interface StockedPartRepository extends CrudRepository<StockedPart, Long> {
}
