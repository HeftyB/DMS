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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public String getTodaysDate() {
        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return date.format(formatter);
    }

    private ModelMap addTimeClockAttributes(ModelMap model,
                                            List<TimeClockPunchSet> punchSetList,
                                            String username,
                                            boolean clockedIn,
                                            String date,
                                            String payStart,
                                            String payEnd) {
        model.addAttribute("punchSetList", punchSetList);
        model.addAttribute("username", username);
        model.addAttribute("clockedInStatus", clockedIn);
        model.addAttribute("currentDate", date);
        model.addAttribute("payPeriodStart", payStart);
        model.addAttribute("payPeriodStart", payEnd);

        return model;
    }

    @GetMapping({"/", ""})
    public String home(Principal principal, ModelMap model) {
        User user = userService.findUserByUsername(principal.getName());

        PayPeriod payPeriod = payPeriodService.getCurrentPayPeriod();
        List<TimeClockPunchSet> punchSetList = timeClockService.findCurrentUsersPunchSetsByPayPeriod(user, payPeriod);


        model = addTimeClockAttributes(model, punchSetList, user.getUsername(), user.getEmployee().isClockedIn(), getTodaysDate(), payPeriod.getStartDate().toString(), payPeriod.getEndDate().toString());


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

    @GetMapping({"/punches", "/punches/"})
    public String getDatesPunches(@RequestParam String date, Principal principal, ModelMap model) throws ParseException {
        User u = userService.findUserByUsername(principal.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        List<TimeClockPunchSet> punchSets = timeClockService
                .findCurrentUsersTimeClockPunchSetsByDate(principal.getName(), sdf.parse(date));


        model = addTimeClockAttributes(model, punchSets,
                u.getUsername(),
                u.getEmployee().isClockedIn(),
                date,
                punchSets.size() > 0 ? punchSets.getFirst().getPayPeriod().getStartDate().toString() : getTodaysDate(),
                punchSets.size() > 0 ? punchSets.getFirst().getPayPeriod().getEndDate().toString() : getTodaysDate());

        return "time_home";

    }
}
