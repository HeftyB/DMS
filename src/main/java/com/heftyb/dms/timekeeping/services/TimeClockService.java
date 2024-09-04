package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.timekeeping.*;
import com.heftyb.dms.users.User;

import java.time.Period;
import java.util.Date;
import java.util.List;

public interface TimeClockService {
    List<TimePunchIn> findAllTimePunchIns();
    List<TimePunchOut> findAllTimePunchOuts();
    TimePunchIn findTimePunchInById(long id);
    TimePunchOut findTimePunchOutById(long id);
    TimePunchIn saveTimePunchIn(TimePunchIn timePunchIn);
    TimePunchOut saveTimePunchOut(TimePunchOut timePunchOut);
    void deleteTimePunchIn(long id);
    void deleteTimePunchOut(long id);


    List<TimeClockPunchSet> findAllTimeClockPunchSets();
    TimeClockPunchSet findTimeClockPunchSetById(long id);
    TimeClockPunchSet saveTimeClockPunchSet(TimeClockPunchSet timeClockPunchSet);
    void deleteTimeClockPunchSet(long id);

    List<TimeClockPunchSet> findCurrentUsersTimeClockPunchSets(User user);
    List<TimeClockPunchSet> findCurrentUsersTimeClockPunchSets(String username);
    List<TimeClockPunchSet> findCurrentUsersTimeClockPunchSetsByDate(String username, Date date);
    List<TimeClockPunchSet> findCurrentUsersPunchSetsByPayPeriod(User user, PayPeriod period);
    TimeClockPunchSet findCurrentTimeClockPunchSetByUser(User u);
    void clockIn(String username, TimePunchCode code);
    void clockOut(String username, TimePunchCode code);

    List<JobTimePunchSet> findAllJobTimePunchSets();
    JobTimePunchSet findJobTimePunchSetById(long id);
    JobTimePunchSet saveJobTimePunchSet(JobTimePunchSet jobTimePunchSet);
    void deleteJobTimePunchSet(long id);
}
