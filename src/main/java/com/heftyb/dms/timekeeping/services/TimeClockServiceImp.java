package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.account.services.PayPeriodService;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.exceptions.TimeClockException;
import com.heftyb.dms.exceptions.UserNotFoundException;
import com.heftyb.dms.repairorder.services.RepairOrderJobService;
import com.heftyb.dms.timekeeping.*;
import com.heftyb.dms.timekeeping.repositories.JobTimePunchSetRepository;
import com.heftyb.dms.timekeeping.repositories.TimeClockPunchSetRepository;
import com.heftyb.dms.timekeeping.repositories.TimePunchInRepository;
import com.heftyb.dms.timekeeping.repositories.TimePunchOutRepository;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.services.UserService;
import jakarta.transaction.Transactional;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.time.Instant;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service(value = "timeClockService")
public class TimeClockServiceImp implements TimeClockService{

    private final TimePunchInRepository inRepo;
    private final TimePunchOutRepository outRepo;
    private final TimeClockPunchSetRepository timeClockPunchRepo;
    private final JobTimePunchSetRepository jobTimeRepo;
    private final EmployeeService employeeService;
    private final RepairOrderJobService repairOrderJobService;
    private final UserService userService;
    private final PayPeriodService payPeriodService;


    public TimeClockServiceImp (
            final TimePunchInRepository timePunchInRepository,
            final TimePunchOutRepository timePunchOutRepository,
            final TimeClockPunchSetRepository timeClockPunchSetRepository,
            final JobTimePunchSetRepository jobTimePunchSetRepository,
            final EmployeeService employeeService,
            final RepairOrderJobService repairOrderJobService,
            final UserService userService,
            final PayPeriodService payPeriodService
    ) {
        inRepo = timePunchInRepository;
        outRepo = timePunchOutRepository;
        timeClockPunchRepo = timeClockPunchSetRepository;
        jobTimeRepo = jobTimePunchSetRepository;
        this.employeeService = employeeService;
        this.repairOrderJobService = repairOrderJobService;
        this.userService = userService;
        this.payPeriodService = payPeriodService;
    }


    @Override
    public List<TimePunchIn> findAllTimePunchIns() {
        List<TimePunchIn> inPunches = new ArrayList<>();
        inRepo.findAll().iterator().forEachRemaining(inPunches::add);
        return inPunches;
    }

    @Override
    public List<TimePunchOut> findAllTimePunchOuts() {
        List<TimePunchOut> outPunches = new ArrayList<>();
        outRepo.findAll().iterator().forEachRemaining(outPunches::add);
        return outPunches;
    }

    @Override
    public TimePunchIn findTimePunchInById(long id) {
        return inRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(errorString("timePunchIn", id))
        );
    }

    @Override
    public TimePunchOut findTimePunchOutById(long id) {
        return outRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(errorString("timePunchOut", id))
        );
    }

    @Transactional
    @Override
    public TimePunchIn saveTimePunchIn(TimePunchIn timePunchIn) {
        TimePunchIn newIn = new TimePunchIn();
        newIn.setEmployee(findEmployeeById(timePunchIn.getEmployee().getId()));
        newIn.setTime(timePunchIn.getTime());
        newIn.setCode(timePunchIn.getCode());

        return inRepo.save(newIn);
    }

    private Employee findEmployeeById(long id) {
        return employeeService.findById(id);
    }


    @Transactional
    @Override
    public TimePunchOut saveTimePunchOut(TimePunchOut timePunchOut) {
        TimePunchOut newOut = new TimePunchOut();
        newOut.setEmployee(findEmployeeById(timePunchOut.getEmployee().getId()));
        newOut.setTime(timePunchOut.getTime());
        newOut.setCode(timePunchOut.getCode());

        return outRepo.save(newOut);
    }

    @Transactional
    @Override
    public void deleteTimePunchIn(long id) {
        findTimePunchInById(id);
        inRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteTimePunchOut(long id) {
        findTimePunchOutById(id);
        outRepo.deleteById(id);
    }

    @Override
    public List<TimeClockPunchSet> findAllTimeClockPunchSets() {
        List<TimeClockPunchSet> timeClockPunchSets = new ArrayList<>();
        timeClockPunchRepo.findAll().iterator().forEachRemaining(timeClockPunchSets::add);
        return timeClockPunchSets;
    }

    @Override
    public TimeClockPunchSet findTimeClockPunchSetById(long id) {
        return timeClockPunchRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(errorString("timeClockPunchSet", id))
        );
    }

    @Transactional
    @Override
    public TimeClockPunchSet saveTimeClockPunchSet(TimeClockPunchSet timeClockPunchSet) {
        TimeClockPunchSet t = new TimeClockPunchSet();
        if (timeClockPunchSet.getId() != 0) {
            t.setId(timeClockPunchSet.getId());
        }
        t.setDate(timeClockPunchSet.getDate());

        // Not nullable
        t.setIn(findTimePunchInById(timeClockPunchSet.getIn().getId()));

        if (timeClockPunchSet.getOut() != null ) {
            t.setOut(findTimePunchOutById(timeClockPunchSet.getOut().getId()));
        }
        t.setEmployee(findEmployeeById(timeClockPunchSet.getEmployee().getId()));

        t.setPayPeriod(payPeriodService.findById(timeClockPunchSet.getPayPeriod().getId()));

        return timeClockPunchRepo.save(t);
    }

    @Transactional
    @Override
    public void deleteTimeClockPunchSet(long id) {
        findTimeClockPunchSetById(id);
        timeClockPunchRepo.deleteById(id);
    }

    public boolean isToday(Date date) {
        Calendar t = Calendar.getInstance();
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return t.get(Calendar.DAY_OF_MONTH) == d.get(Calendar.DAY_OF_MONTH) &&
                t.get(Calendar.MONTH) == d.get(Calendar.MONTH) &&
                t.get(Calendar.YEAR) == d.get(Calendar.YEAR);
    }

    @Override
    public List<TimeClockPunchSet> findCurrentUsersTimeClockPunchSets(User user) {
        return timeClockPunchRepo.findByEmployee(user.getEmployee());
    }

    @Override
    public List<TimeClockPunchSet> findCurrentUsersPunchSetsByPayPeriod(User user, PayPeriod period) {
        return timeClockPunchRepo.findByEmployeeAndPayPeriod(user.getEmployee(), period);
    }

    @Override
    public TimeClockPunchSet findCurrentTimeClockPunchSetByUser(User user) {
        User u = userService.getUserByID(user.getId())
                .orElseThrow(()-> new UserNotFoundException(
                        String.format("Error: could not find user: %s", user.getId())
                ));
        List<TimeClockPunchSet> punchSets = timeClockPunchRepo.findByEmployee(u.getEmployee())
                .stream().filter(
                        ps -> isToday(ps.getDate())
                )
                .collect(Collectors.toList());
        punchSets.sort(Comparator.comparing(TimeClockPunchSet::getInPunchTime).reversed());

        if(punchSets.get(0).getOut() == null) {
            return punchSets.get(0);
        } else {
            TimeClockPunchSet timeClockPunchSet = new TimeClockPunchSet();
            timeClockPunchSet.setEmployee(u.getEmployee());
            timeClockPunchSet.setDate(Date.from(Instant.now()));
            return timeClockPunchSet;
        }
    }

    @Override
    public void clockIn(String username, TimePunchCode code) {
        User user = userService.findUserByUsername(username);
        TimePunchIn punchIn = new TimePunchIn(user.getEmployee(), Date.from(Instant.now()), code);
        punchIn = saveTimePunchIn(punchIn);

        TimeClockPunchSet punchSet = new TimeClockPunchSet(punchIn.getTime(), punchIn, user.getEmployee(), payPeriodService.getCurrentPayPeriod());
        saveTimeClockPunchSet(punchSet);
        employeeService.setEmployeeClockedIn(user.getEmployee().getId(), true);
    }

    @Override
    public void clockOut(String username, TimePunchCode code) {
        User user = userService.findUserByUsername(username);
        TimePunchOut punchOut = new TimePunchOut(user.getEmployee(), Date.from(Instant.now()), code);

        TimeClockPunchSet punchSet = findCurrentTimeClockPunchSetByUser(user);

        if(punchSet.getIn() == null || punchSet.getOut() != null) {
            throw new TimeClockException(String.format("Error: Could not clock out, no matching TimeClockPunchSet was found!"));
        } else {
            punchOut = saveTimePunchOut(punchOut);
            punchSet.setOut(punchOut);
            saveTimeClockPunchSet(punchSet);
            employeeService.setEmployeeClockedIn(user.getEmployee().getId(), false);
        }
    }

    @Override
    public List<JobTimePunchSet> findAllJobTimePunchSets() {
        List<JobTimePunchSet> jobTimePunchSetList = new ArrayList<>();
        jobTimeRepo.findAll().iterator().forEachRemaining(jobTimePunchSetList::add);
        return jobTimePunchSetList;
    }

    @Override
    public JobTimePunchSet findJobTimePunchSetById(long id) {
        return jobTimeRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(errorString("jobTimePunchSet", id))
        );
    }

    @Transactional
    @Override
    public JobTimePunchSet saveJobTimePunchSet(JobTimePunchSet jobTimePunchSet) {
        JobTimePunchSet j = new JobTimePunchSet();
        j.setDate(jobTimePunchSet.getDate());
        j.setIn(findTimePunchInById(jobTimePunchSet.getIn().getId()));
        j.setOut(findTimePunchOutById(jobTimePunchSet.getOut().getId()));
        j.setEmployee(employeeService.findById(jobTimePunchSet.getEmployee().getId()));
        j.setJob(repairOrderJobService.findById(jobTimePunchSet.getJob().getId()));

        return jobTimeRepo.save(j);
    }

    @Transactional
    @Override
    public void deleteJobTimePunchSet(long id) {
        findJobTimePunchSetById(id);
        jobTimeRepo.deleteById(id);
    }

    private String errorString(String name, long id) {
        return String.format(
                "TimeClockService Error: could not find %s id %s", name, id
        );
    }
}
