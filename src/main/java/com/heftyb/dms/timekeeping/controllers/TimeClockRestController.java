package com.heftyb.dms.timekeeping.controllers;

import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimePunchCode;
import com.heftyb.dms.timekeeping.services.TimeClockService;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/timeclock")
public class TimeClockRestController {

    private final TimeClockService timeClockService;
    private final UserService userService;

    public TimeClockRestController(final TimeClockService timeClockService,
                                   final UserService userService) {
        this.timeClockService = timeClockService;
        this.userService = userService;
    }


    @GetMapping("/")
    public ResponseEntity<?> getUsersCurrentTimeClock(Principal principal) {
        User u = userService.findUserByUsername(principal.getName());
        TimeClockPunchSet punchSet = timeClockService.findCurrentTimeClockPunchSetByUser(u);

        return new ResponseEntity<>(punchSet, HttpStatus.OK);
    }

    @PostMapping("/in")
    public ResponseEntity<?> clockCurrentUserIn(@RequestParam TimePunchCode code, Principal principal) {
        timeClockService.clockIn(principal.getName(), code);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/out")
    public ResponseEntity<?> clockCUrrentUserOut(@RequestParam TimePunchCode code, Principal principal) {
        timeClockService.clockOut(principal.getName(), code);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
