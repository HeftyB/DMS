package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.timekeeping.JobTimePunchSet;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimePunchIn;
import com.heftyb.dms.timekeeping.TimePunchOut;

import java.util.List;

public interface TimeClockService {
    List<TimePunchIn> findAllTimePunchIns();
    List<TimePunchOut> findAllTimePunchOuts();
    TimePunchIn findTimePunchInById(long id);
    TimePunchOut findTimePunchOutById(long id);
    TimePunchIn saveTimePunchIn(TimePunchIn timePunchIn);
    TimePunchOut saveTimePunchOut(TimePunchIn timePunchIn);
    void deleteTimePunchIn(long id);
    void deleteTimePunchOut(long id);


    List<TimeClockPunchSet> findAllTimeClockPunchSets();
    TimeClockPunchSet findTimeClockPunchSetById(long id);
    TimeClockPunchSet saveTimeClockPunchSet(TimeClockPunchSet timeClockPunchSet);
    void deleteTimeClockPunchSet(long id);

    List<JobTimePunchSet> findAllJobTimePunchSets();
    JobTimePunchSet findJobTimePunchSetById(long id);
    JobTimePunchSet saveJobTimePunchSet(JobTimePunchSet jobTimePunchSet);
    void deleteJobTimePunchSet(long id);
}
