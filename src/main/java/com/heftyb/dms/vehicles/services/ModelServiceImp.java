package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.vehicles.Manufacturer;
import com.heftyb.dms.vehicles.Model;
import com.heftyb.dms.vehicles.repositories.ModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "modelService")
public class ModelServiceImp implements ModelService{

    private final ModelRepository modelRepo;
    private final ManufacturerService manufacturerService;


    public ModelServiceImp(final ModelRepository modelRepository, final ManufacturerService manufacturerService) {
        modelRepo = modelRepository;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public List<Model> findAll() {
        ArrayList<Model> models = new ArrayList<>();
        modelRepo.findAll().iterator().forEachRemaining(models::add);
        return models;
    }

    @Override
    public List<Model> findByManufacturer(String name) {
        return modelRepo.findByManufacturer_NameContaining(name);
    }

    @Override
    public Model findByName(String name) {
        Model m = modelRepo.findByName(name).orElseThrow(
                () -> new DataNotFoundException(String.format("ModelService Error: could not find model name %s", name))
        );

        return m;
    }

    @Override
    public List<Model> findByNameContaining(String name) {
        List<Model> models = new ArrayList<>();
        modelRepo.findByNameContaining(name).iterator().forEachRemaining(models::add);
        return models;
    }

    @Override
    public Model findById(long id) {
        return modelRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format("ModelService Error: could not find Model id %g", id))
        );
    }

    @Transactional
    @Override
    public Model save(Model model) {

        Model newModel = new Model();

        newModel.setName(model.getName());

        Manufacturer manufacturer = manufacturerService.findById(model.getManufacturer().getId());

        newModel.setManufacturer(manufacturer);

        return modelRepo.save(newModel);
    }

    @Transactional
    @Override
    public Model update(Model model) {
        return null;
    }

    @Transactional
    @Override
    public void delete(long id) {
        findById(id);
        modelRepo.deleteById(id);
    }
}
