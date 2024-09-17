package com.heftyb.dms.crm;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public class Zipcode {
    @NotNull
//    @Size(max = 5)
    private String zip;

    @Size(max = 4)
    private String plus4;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPlus4() {
        return plus4;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    @Override
    public String toString() {
        if (plus4 != null) return String.format("%s-%s", zip, plus4);
        else return zip;
    }
}
