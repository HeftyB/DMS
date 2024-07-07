package com.heftyb.dms.vehicles.services;

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


    public ModelServiceImp(final ModelRepository modelRepository) {
        modelRepo = modelRepository;
    }

    @Override
    public List<Model> findAll() {
        ArrayList<Model> models = new ArrayList<>();
        modelRepo.findAll().iterator().forEachRemaining(models::add);
        return models;
    }

    @Override
    public List<Model> findByManufacturer(String name) {
        modelRepo.findByManufacturer_Name(name)
        return null;
    }

    @Override
    public Model findByName(String name) {
        return null;
    }

    @Override
    public List<Model> findByNameContaining(String name) {
        return null;
    }

    @Override
    public Model findById(long id) {
        return null;
    }

    @Override
    public Model save(Model model) {
        return null;
    }

    @Override
    public Model update(Model model) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
