package com.heftyb.dms.vehicles;

import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturers")
public class Manufacturer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List<WMI> wmis;

    @OneToMany(mappedBy = "manufacturer")
    private List<Model> models;

    public Manufacturer() {
        wmis = new ArrayList<>();
        models = new ArrayList<>();
    }

    public Manufacturer(String name) {
        this.name = name;
        this.wmis = new ArrayList<>();
        this.models = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WMI> getWmis() {
        return wmis;
    }

    public void setWmis(ArrayList<WMI> wmis) {
        this.wmis = wmis;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }

    public boolean addWmi(WMI wmi) {
        return wmis.add(wmi);
    }

    public boolean addModel(Model model) {
        return models.add(model);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
