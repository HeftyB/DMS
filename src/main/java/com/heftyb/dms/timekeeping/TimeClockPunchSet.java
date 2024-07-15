package com.heftyb.dms.timekeeping;

import com.heftyb.dms.account.Auditable;
import com.heftyb.dms.crm.Employee;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "timeClockPunchSets")
public class TimeClockPunchSet extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date date;

    @OneToOne
    @JoinColumn()
    private TimePunchIn in;

    @OneToOne
    @JoinColumn()
    private TimePunchOut out;

    @ManyToOne()
    @JoinColumn()
    private Employee employee;



    public TimeClockPunchSet() {
    }



    public TimeClockPunchSet(TimePunchIn timein) {
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
