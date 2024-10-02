package com.heftyb.dms;

import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimePunchCode;
import com.heftyb.dms.timekeeping.services.TimeClockService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {
    private final TimeClockService timeClockService;

    public ScheduledTasks(final TimeClockService timeClockService) {
        this.timeClockService = timeClockService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void clockAllUsersOut() {
        List<TimeClockPunchSet> punchSets = timeClockService.findAllTimeClockPunchSets()
                .stream().filter(ps -> ps.getOut() == null).collect(Collectors.toList());

        for(TimeClockPunchSet ps : punchSets) {
            timeClockService.clockOut(ps.getEmployee().getUser().getUsername(), TimePunchCode.SYSTEM);
        }
    }
}
