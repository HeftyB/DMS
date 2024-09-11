package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.vehicles.Manufacturer;
import com.heftyb.dms.vehicles.Model;
import com.heftyb.dms.vehicles.WMI;
import com.heftyb.dms.vehicles.repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service(value = "manufacturerService")
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manRepo;


    public ManufacturerServiceImp(final ManufacturerRepository manRepo) { this.manRepo = manRepo; }

    @Override
    public List<Manufacturer> findAll() {
        ArrayList<Manufacturer> manufacturers = new ArrayList<>();
        manRepo.findAll().iterator().forEachRemaining(manufacturers::add);
        return manufacturers;
    }

    @Override
    public Manufacturer findById(long id) {
        return manRepo.findById(id).orElseThrow(() -> new DataNotFoundException("ManufacturerService Error: Could not find Manufacturer id " + id + "\n"));
    }

    @Override
    public Manufacturer findByName(String name) {
        Manufacturer m = manRepo.findByNameIgnoreCase(name).orElseThrow(() -> new DataNotFoundException(String.format("ManufacturerService Error: could not find Manufacturer name %s", name)));
        return m;
    }

    @Override
    public List<Manufacturer> findByNameContaining(String name) {

        return manRepo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public void delete(long id) {
        manRepo.findById(id).orElseThrow(() -> new RuntimeException("ManufacturerService Error: Could not find Manufacturer id " + id + "\n"));
        manRepo.deleteById(id);
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        Manufacturer newManufacturer = new Manufacturer();

        newManufacturer.setName(manufacturer.getName());
        newManufacturer.setWmis(manufacturer.getWmis());
        newManufacturer.setModels(manufacturer.getModels());

       return manRepo.save(newManufacturer);
    }

    @Override
    public Manufacturer update(long id, Manufacturer manufacturer) {

        Manufacturer m = findById(id);

        if (manufacturer.getName() != null) m.setName(manufacturer.getName());
        if (manufacturer.getModels() != null) m.setModels(manufacturer.getModels());
        if (manufacturer.getWmis() != null) m.setWmis(manufacturer.getWmis());
        
        return manRepo.save(m);
    }
}
