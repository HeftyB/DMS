package com.heftyb.dms.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heftyb.dms.dao.Auditable;
import jakarta.persistence.*;

@Embeddable
public class PhoneNumber {
    private String number;
    private String ext;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public boolean hasExt() { return ext != null; }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "number='" + number + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
