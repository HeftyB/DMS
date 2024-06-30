package com.heftyb.dms.timekeeping;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "timepunches")
public class TimePunch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date date;

    @OneToOne
    private TimePunchIn in;

    @OneToOne
    private TimePunchOut out;

    public TimePunch() {
    }

    public TimePunch(TimePunchIn timein) {
        this.in = timein;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TimePunchIn getIn() {
        return in;
    }

    public void setIn(TimePunchIn in) {
        this.in = in;
    }

    public TimePunchOut getOut() {
        return out;
    }

    public void setOut(TimePunchOut out) {
        this.out = out;
    }
}
