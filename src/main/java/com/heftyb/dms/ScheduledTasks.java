package com.heftyb.dms;

import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimePunchCode;
import com.heftyb.dms.timekeeping.services.TimeClockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final TimeClockService timeClockService;

    public ScheduledTasks(final TimeClockService timeClockService) {
        this.timeClockService = timeClockService;
    }

    /**
     * Closes every shift still open at midnight. Each shift is closed directly rather than looked up
     * by date, since a shift open at 00:00 started the previous day. A failure on one shift is logged
     * and does not stop the rest.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void clockAllUsersOut() {
        for (TimeClockPunchSet shift : timeClockService.findOpenTimeClockPunchSets()) {
            try {
                timeClockService.closeTimeClockPunchSet(shift, TimePunchCode.SYSTEM);
            } catch (RuntimeException e) {
                log.error("Could not auto clock out time clock punch set {}", shift.getId(), e);
            }
        }
    }
}
