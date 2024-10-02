package com.heftyb.dms.vehicles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.repairorder.RepairOrder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "vehicles")
public class Vehicle extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String vin;

    private int modelYear;
    private String make;
    private String model;
    private String manufacturer;
    private String plantCompanyName;
    private String plantCity;
    private String plantState;
    private String transmissionStyle;
    private String driveType;
    private String engineModel;
    private String engineManufacturer;
    private String engineType; // configuration + cylinders + valvetrain design
    private String displacementL;
    private String fuelType;
    private String bodyClass;
    private String basePrice;
    private String doors;
    private String trim;
    private String trim2;
    private String vehicleType;


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

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RepairOrder> ServiceHistory;

    @ManyToOne
    @JoinColumn
    private Customer customer;


    public Vehicle() {
        ServiceHistory = new ArrayList<>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        if (vin != null) this.vin = vin.toUpperCase(Locale.ROOT);
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        if (make != null) this.make = make.toUpperCase(Locale.ROOT);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model != null) this.model = model.toUpperCase(Locale.ROOT);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer != null) this.manufacturer = manufacturer.toUpperCase(Locale.ROOT);
    }

    public String getPlantCompanyName() {
        return plantCompanyName;
    }

    public void setPlantCompanyName(String plantCompanyName) {
        if (plantCompanyName != null) this.plantCompanyName = plantCompanyName.toUpperCase(Locale.ROOT);
    }

    public String getPlantCity() {
        return plantCity;
    }

    public void setPlantCity(String plantCity) {
        if (plantCity != null) this.plantCity = plantCity.toUpperCase(Locale.ROOT);
    }

    public String getPlantState() {
        return plantState;
    }

    public void setPlantState(String plantState) {
        if (plantState != null) this.plantState = plantState.toUpperCase(Locale.ROOT);
    }

    public String getTransmissionStyle() {
        return transmissionStyle;
    }

    public void setTransmissionStyle(String transmissionStyle) {
        if (transmissionStyle != null) this.transmissionStyle = transmissionStyle.toUpperCase(Locale.ROOT);
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        if (driveType != null) this.driveType = driveType.toUpperCase(Locale.ROOT);
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        if (engineModel != null) this.engineModel = engineModel.toUpperCase(Locale.ROOT);
    }

    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public void setEngineManufacturer(String engineManufacturer) {
        if (engineManufacturer != null) this.engineManufacturer = engineManufacturer.toUpperCase(Locale.ROOT);
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        if (engineType != null) this.engineType = engineType.toUpperCase(Locale.ROOT);
    }

    public String getDisplacementL() {
        return displacementL;
    }

    public void setDisplacementL(String displacementL) {
        if (displacementL != null) this.displacementL = displacementL.toUpperCase(Locale.ROOT);
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        if (fuelType != null) this.fuelType = fuelType.toUpperCase(Locale.ROOT);
    }

    public String getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        if (bodyClass != null) this.bodyClass = bodyClass.toUpperCase(Locale.ROOT);
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        if (basePrice != null) this.basePrice = basePrice.toUpperCase(Locale.ROOT);
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        if (doors != null) this.doors = doors.toUpperCase(Locale.ROOT);
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        if (trim != null) this.trim = trim.toUpperCase(Locale.ROOT);
    }

    public String getTrim2() {
        return trim2;
    }

    public void setTrim2(String trim2) {
        if (trim2 != null) this.trim2 = trim2.toUpperCase(Locale.ROOT);
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        if (vehicleType != null) this.vehicleType = vehicleType.toUpperCase(Locale.ROOT);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color != null) this.color = color.toUpperCase(Locale.ROOT);
    }

    public List<RepairOrder> getServiceHistory() {
        return ServiceHistory;
    }

    public void setServiceHistory(List<RepairOrder> serviceHistory) {
        ServiceHistory = serviceHistory;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getVehicleInfo() {
        return String.format("%s-%s-%s  %s  %s",
                modelYear, make, model, color, vin);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", modelYear=" + modelYear +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", plantCompanyName='" + plantCompanyName + '\'' +
                ", plantCity='" + plantCity + '\'' +
                ", plantState='" + plantState + '\'' +
                ", transmissionStyle='" + transmissionStyle + '\'' +
                ", driveType='" + driveType + '\'' +
                ", engineModel='" + engineModel + '\'' +
                ", engineManufacturer='" + engineManufacturer + '\'' +
                ", engineType='" + engineType + '\'' +
                ", displacementL='" + displacementL + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", bodyClass='" + bodyClass + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", doors='" + doors + '\'' +
                ", trim='" + trim + '\'' +
                ", trim2='" + trim2 + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", color='" + color + '\'' +
                ", ServiceHistory=" + ServiceHistory +
                ", customer=" + customer +
                '}';
    }
}
