package com.heftyb.dms.vehicles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.repairorder.RepairOrder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String vin;

    private int modelYear;

    @ManyToOne
    private Manufacturer make;

    @ManyToOne
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
    @JsonIgnore
    private List<RepairOrder> repairOrders;

    @ManyToOne
    @JoinColumn
    private Customer customer;


    public Vehicle() {

    }

    public Vehicle(String vin, int modelYear, Manufacturer make, Model model) {
        this.vin = vin;
        this.modelYear = modelYear;
        this.make = make;
        this.model = model;
    }

    public Vehicle(String vin, int modelYear, Manufacturer make, Model model, String trim, String engine, String color) {
        this.vin = vin;
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public List<RepairOrder> getRepairOrders() {
        return repairOrders;
    }

    public void setRepairOrders(ArrayList<RepairOrder> repairOrders) {
        this.repairOrders = repairOrders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
