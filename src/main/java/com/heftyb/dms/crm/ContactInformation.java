package com.heftyb.dms.crm;

import jakarta.persistence.*;

@Embeddable
public class ContactInformation {

    private String contactName;
    private String contactEmail;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "contact_address_line_1")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "contact_address_line_2")),
            @AttributeOverride(name = "city", column = @Column(name = "contact_city")),
            @AttributeOverride(name = "state", column = @Column(name = "contact_state")),
            @AttributeOverride(name = "zip.zip", column = @Column(name = "contact_zip")),
            @AttributeOverride(name = "zip.plus4", column = @Column(name = "contact_zip+4"))
    })
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "primary_phone_number")),
            @AttributeOverride(name = "ext", column = @Column(name = "primary_phone_ext"))
    })
    private PhoneNumber primaryPhone;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "alt1_phone_number")),
            @AttributeOverride(name = "ext", column = @Column(name = "alt1_phone_ext"))
    })
    private PhoneNumber altPhone1;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "alt2_phone_number")),
            @AttributeOverride(name = "ext", column = @Column(name = "alt2_phone_ext"))
    })
    private PhoneNumber altPhone2;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "fax_phone_number")),
            @AttributeOverride(name = "ext", column = @Column(name = "fax_phone_ext"))
    })
    private PhoneNumber fax;

    @Column(name = "contact_information_notes")
    private String notes;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PhoneNumber getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(PhoneNumber primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public PhoneNumber getAltPhone1() {
        return altPhone1;
    }

    public void setAltPhone1(PhoneNumber altPhone1) {
        this.altPhone1 = altPhone1;
    }

    public PhoneNumber getAltPhone2() {
        return altPhone2;
    }

    public void setAltPhone2(PhoneNumber altPhone2) {
        this.altPhone2 = altPhone2;
    }

    public PhoneNumber getFax() {
        return fax;
    }

    public void setFax(PhoneNumber fax) {
        this.fax = fax;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public boolean containsMatchingContactInformation(ContactInformation contactInformation) {
        return contactInformation.getContactName() == contactName
                || contactInformation.getPrimaryPhone() == primaryPhone
                || contactInformation.getAltPhone1() == altPhone1
                || contactInformation.getAltPhone2() == altPhone2
                || contactInformation.getFax() == fax
                || contactInformation.getAddress() == address
                || contactInformation.getContactEmail() == contactEmail;
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
                "contactName='" + contactName + '\'' +
                ", mailingAddress=" + address +
                ", primaryPhone=" + primaryPhone +
                ", altPhone1=" + altPhone1 +
                ", altPhone2=" + altPhone2 +
                ", fax=" + fax +
                ", notes='" + notes + '\'' +
                '}';
    }
}
