package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment extends Auditable {

    @Id
    @GeneratedValue
    private long id;

    @Basic
    private LocalDateTime startDateTime;

    @Basic
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn
    private Employee advisor;

    @ElementCollection
    private List<String> concerns;

    @OneToMany(mappedBy = "appointment")
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
}
