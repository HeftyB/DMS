package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.vehicles.Manufacturer;
import com.heftyb.dms.vehicles.Model;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.WMI;
import com.heftyb.dms.vehicles.repositories.ManufacturerRepository;
import com.heftyb.dms.vehicles.repositories.ModelRepository;
import com.heftyb.dms.vehicles.repositories.WMIRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service(value = "manufacturerService")
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manRepo;
    private final WMIRepository wmiRepo;
    private final ModelRepository modelRepo;


    public ManufacturerServiceImp(final ManufacturerRepository manRepo,
                                  final WMIRepository wmiRepo,
                                  final ModelRepository modelRepo) {
        this.manRepo = manRepo;
        this.wmiRepo = wmiRepo;
        this.modelRepo = modelRepo;
    }

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
        Manufacturer m = manRepo.findByName(name).orElseThrow(() -> new DataNotFoundException(String.format("ManufacturerService Error: could not find Manufacturer name %s", name)));
        return m;
    }

    @Override
    public List<Manufacturer> findByNameContaining(String name) {

        return manRepo.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    @Override
    public void delete(long id) {
        manRepo.findById(id).orElseThrow(() -> new RuntimeException("ManufacturerService Error: Could not find Manufacturer id " + id + "\n"));
        manRepo.deleteById(id);
    }

    @Transactional
    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        Manufacturer newManufacturer = new Manufacturer();

        newManufacturer.setName(manufacturer.getName());

        newManufacturer = manRepo.save(newManufacturer);

        for (WMI w: manufacturer.getWmis()) {

            Optional<WMI > ww = wmiRepo.findById(w.getId());

            if(ww.isEmpty()) {
                ww = Optional.of(wmiRepo.save(new WMI(w.getName(), w.getWmi(), newManufacturer)));
            }

            newManufacturer.addWmi(ww.get());
        }

        for (Model m: manufacturer.getModels()) {
            Optional<Model> mm = modelRepo.findById(m.getId());

            if(!mm.isPresent()) {
                mm = Optional.of(modelRepo.save(new Model(m.getName(), newManufacturer)));
            }

            newManufacturer.addModel(mm.get());
        }

        return newManufacturer;
    }

    @Transactional
    @Override
    public Manufacturer update(long id, Manufacturer manufacturer) {
        return null;
    }

    @Override
    public WMI addWMI(WMI wmi) {
        WMI w = new WMI();

        w.setName(wmi.getName());

        Manufacturer m = findById(wmi.getManufacturer().getId());

        w.setManufacturer(m);
        return wmiRepo.save(w);
    }
}
