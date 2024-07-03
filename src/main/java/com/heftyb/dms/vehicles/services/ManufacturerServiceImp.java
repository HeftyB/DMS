package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.vehicles.Manufacturer;
import com.heftyb.dms.vehicles.repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manRepo;

    @Override
    public List<Manufacturer> findAll() {
        ArrayList<Manufacturer> manufacturers = new ArrayList<>();
        manRepo.findAll().iterator().forEachRemaining(manufacturers::add);
        return manufacturers;
    }

    @Override
    public Manufacturer findById(long id) {
        return null;
    }

    @Override
    public Manufacturer findByName(String name) {
        return null;
    }

    @Override
    public List<Manufacturer> findByNameContaining(String name) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Manufacturer update(long id, Manufacturer manufacturer) {
        return null;
    }

    public ManufacturerServiceImp(final ManufacturerRepository manRepo) { this.manRepo = manRepo; }
}
