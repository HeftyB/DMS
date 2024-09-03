package com.heftyb.dms.timekeeping.controllers;

import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.account.services.PayPeriodService;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimePunchCode;
import com.heftyb.dms.timekeeping.services.TimeClockService;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("timeclock")
public class TimeClockController {
    private final TimeClockService timeClockService;
    private final UserService userService;
    private final PayPeriodService payPeriodService;

    public TimeClockController(final TimeClockService timeClockService,
                               final UserService userService,
                               final PayPeriodService payPeriodService) {
        this.timeClockService = timeClockService;
        this.userService = userService;
        this.payPeriodService = payPeriodService;
    }
    @GetMapping("/")
    public String home(Principal principal, ModelMap model) {
        User user = userService.findUserByUsername(principal.getName());

        PayPeriod payPeriod = payPeriodService.getCurrentPayPeriod();
        List<TimeClockPunchSet> punchSetList = timeClockService.findCurrentUsersPunchSetsByPayPeriod(user, payPeriod);
        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        model.addAttribute("punchSetList", punchSetList);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("clockedInStatus", user.getEmployee().isClockedIn());
        model.addAttribute("currentDate", date.format(formatter));
        model.addAttribute("payPeriodStart", payPeriod.getStartDate());
        model.addAttribute("payPeriodStart", payPeriod.getStartDate());
        model.addAttribute("payPeriodEnd", payPeriod.getEndDate());

        return "time_home";
    }

    @PostMapping("/in")
    public String clockIn(@RequestParam TimePunchCode code, Principal principal) {
        timeClockService.clockIn(principal.getName(), code);

        return "redirect:/timeclock/";
    }

    @PostMapping("/out")
    public String clockOut(@RequestParam TimePunchCode code, Principal principal) {
        timeClockService.clockOut(principal.getName(), code);

        return "redirect:/timeclock/";
    }
}
