package com.heftyb.dms.crm;

import com.heftyb.dms.account.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "contactsInformation")
public class ContactInformation extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String phone;
    private String email;
    private String notes;

    public ContactInformation() {
    }

    public ContactInformation(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.notes = "";
    }

    public ContactInformation(String name, String phone, String email, String notes) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
