package com.heftyb.dms.inventory.repositories;

import com.heftyb.dms.inventory.Part;
import org.springframework.data.repository.CrudRepository;

public interface PartRepository extends CrudRepository<Part, Long> {
}
