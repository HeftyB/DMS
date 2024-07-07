package com.heftyb.dms.vehicles.services;

import com.heftyb.dms.vehicles.Model;

import java.util.List;

public interface ModelService {
    List<Model> findAll();
    List<Model> findByManufacturer(String name);
    Model findByName(String name);
    List<Model> findByNameContaining(String name);

    Model findById(long id);
    Model save (Model model);
    Model update(Model model);
    void delete(long id);
}
