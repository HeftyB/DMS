package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.dao.Auditable;
import com.heftyb.dms.vehicles.Vehicle;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private LocalDateTime startDateTime;

    @Basic
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn
    private Employee advisor;

    private String year;
    private String make;
    private String model;
    private String vin;

    @ElementCollection
    private List<String> concerns;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<AppointmentBlock> blocks;

    @Embedded
    private ContactInformation contactInformation;

    private String confirmationCode;

    public Appointment() {
        blocks = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime start) {
        this.startDateTime = start;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime end) {
        this.endDateTime = end;
    }

    public Employee getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Employee advisor) {
        this.advisor = advisor;
    }

    public List<String> getConcerns() {
        return concerns;
    }

    public void setConcerns(List<String> concerns) {
        this.concerns = concerns;
    }

    public List<AppointmentBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<AppointmentBlock> blocks) {
        this.blocks = blocks;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String startTimeCleanString() { return startDateTime.toLocalTime().toString().replace(":", ""); }
    public String endTimeCleanString() { return endDateTime.toLocalTime().toString().replace(":", ""); }

    public String getAppointmentInfo() {
        return String.format(
                "#: %s Start: %s, End: %s \n Name: %s",
                id, startDateTime.toLocalTime(), endDateTime.toLocalTime(), contactInformation.getContactName()
        );
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", advisor=" + advisor +
                ", concerns=" + concerns +
                ", blocks=" + blocks +
                ", contactInformation=" + contactInformation +
                ", confirmationCode='" + confirmationCode + '\'' +
                '}';
    }
}
