package com.heftyb.dms.vehicles;

import com.heftyb.dms.account.models.po.PurchaseOrder;
import com.heftyb.dms.repairorder.RepairOrder;
import jakarta.persistence.*;

import java.util.ArrayList;

public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String vin;

    private int modelYear;

    private Manufacturer make;
    private Model model;
    private String trim;
    private String engine;
    private String color;

//    private String stock;
//    private double msrp;
//    private VehicleSale lastSalePrice;
//    private VehicleSale initialSalePrice;
//    private ArrayList<VehicleSale> sales;
//
//    private LienHolder lienHolder;
//    private double apr;
//    private AutoLoan autoLoan; /* rate, balance, principal, interest, down payment */
//    private ArrayList<PurchaseOrder> purchaseOrders;
//
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private ArrayList<RepairOrder> repairOrders;




    public Vehicle() {

    }

    public Vehicle(int modelYear, Manufacturer make, Model model) {
        this.modelYear = modelYear;
        this.make = make;
        this.model = model;
    }

    public Vehicle(int modelYear, Manufacturer make, Model model, String trim, String engine, String color) {
        this.modelYear = modelYear;
        this.make = make;
        this.model = model;
        this.trim = trim;
        this.engine = engine;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public Manufacturer getMake() {
        return make;
    }

    public void setMake(Manufacturer make) {
        this.make = make;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", year=" + modelYear +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", trim='" + trim + '\'' +
                ", engine='" + engine + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
