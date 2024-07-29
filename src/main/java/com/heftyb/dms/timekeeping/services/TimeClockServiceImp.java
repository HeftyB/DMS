package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.services.RepairOrderJobService;
import com.heftyb.dms.timekeeping.JobTimePunchSet;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimePunchIn;
import com.heftyb.dms.timekeeping.TimePunchOut;
import com.heftyb.dms.timekeeping.repositories.JobTimePunchSetRepository;
import com.heftyb.dms.timekeeping.repositories.TimeClockPunchSetRepository;
import com.heftyb.dms.timekeeping.repositories.TimePunchInRepository;
import com.heftyb.dms.timekeeping.repositories.TimePunchOutRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Transactional
@Service(value = "timeClockService")
public class TimeClockServiceImp implements TimeClockService{

    private final TimePunchInRepository inRepo;
    private final TimePunchOutRepository outRepo;
    private final TimeClockPunchSetRepository timeClockPunchRepo;
    private final JobTimePunchSetRepository jobTimeRepo;
    private final EmployeeService employeeService;
    private final RepairOrderJobService repairOrderJobService;


    public TimeClockServiceImp (
            final TimePunchInRepository timePunchInRepository,
            final TimePunchOutRepository timePunchOutRepository,
            final TimeClockPunchSetRepository timeClockPunchSetRepository,
            final JobTimePunchSetRepository jobTimePunchSetRepository,
            final EmployeeService employeeService,
            final RepairOrderJobService repairOrderJobService
    ) {
        inRepo = timePunchInRepository;
        outRepo = timePunchOutRepository;
        timeClockPunchRepo = timeClockPunchSetRepository;
        jobTimeRepo = jobTimePunchSetRepository;
        this.employeeService = employeeService;
        this.repairOrderJobService = repairOrderJobService;
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
    public TimePunchOut saveTimePunchOut(TimePunchIn timePunchIn) {
        TimePunchOut newOut = new TimePunchOut();
        newOut.setEmployee(findEmployeeById(timePunchIn.getEmployee().getId()));
        newOut.setTime(timePunchIn.getTime());
        newOut.setCode(timePunchIn.getCode());

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
        t.setDate(timeClockPunchSet.getDate());
        t.setIn(findTimePunchInById(timeClockPunchSet.getIn().getId()));
        t.setOut(findTimePunchOutById(timeClockPunchSet.getOut().getId()));
        t.setEmployee(findEmployeeById(timeClockPunchSet.getEmployee().getId()));

        return timeClockPunchRepo.save(t);
    }

    @Transactional
    @Override
    public void deleteTimeClockPunchSet(long id) {
        findTimeClockPunchSetById(id);
        timeClockPunchRepo.deleteById(id);
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
