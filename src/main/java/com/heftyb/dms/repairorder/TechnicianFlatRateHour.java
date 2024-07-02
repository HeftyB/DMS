package com.heftyb.dms.repairorder;

import com.heftyb.dms.crm.Employee;
import jakarta.persistence.*;


@Entity
@Table(name = "technicianFlatRateHours")
public class TechnicianFlatRateHour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne()
    @JoinColumn()
    private Employee technician;

    private double flatRateHours;

    @ManyToOne
    @JoinColumn()
    private RepairOrderJob job;

    public TechnicianFlatRateHour() {
    }

    public TechnicianFlatRateHour(Employee technician, double flatRateHours, RepairOrderJob job) {
        this.technician = technician;
        this.flatRateHours = flatRateHours;
        this.job = job;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getTechnician() {
        return technician;
    }

    public void setTechnician(Employee technician) {
        this.technician = technician;
    }

    public double getFlatRateHours() {
        return flatRateHours;
    }

    public void setFlatRateHours(double flatRateHours) {
        this.flatRateHours = flatRateHours;
    }

    public RepairOrderJob getJob() {
        return job;
    }

    public void setJob(RepairOrderJob job) {
        this.job = job;
    }
}
