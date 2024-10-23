package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.inventory.WorkOrderJobPart;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.TechnicianFlatRateHour;
import com.heftyb.dms.repairorder.WorkOrderJob;
import com.heftyb.dms.repairorder.repositories.WorkOrderJobRepository;
import com.heftyb.dms.timekeeping.JobTimePunchSet;
import com.heftyb.dms.timekeeping.services.TimeClockService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("workOrderJobService")
public class WorkOrderJobServiceImp implements WorkOrderJobService {

    private final WorkOrderJobRepository jobRepo;
    private final RepairOrderService roService;
//    private final TimeClockService timeClockService;
    private final TechnicianFlatRateHourService technicianFlatRateHourService;

    public WorkOrderJobServiceImp(final WorkOrderJobRepository workOrderJobRepository,
                                  final RepairOrderService repairOrderService,
//                                  final TimeClockService timeClockService,
                                  final TechnicianFlatRateHourService technicianFlatRateHourService) {
        jobRepo = workOrderJobRepository;
        roService = repairOrderService;
//        this.timeClockService = timeClockService;
        this.technicianFlatRateHourService = technicianFlatRateHourService;
    }


    @Override
    public List<WorkOrderJob> findAll() {
        List<WorkOrderJob> jobs = new ArrayList<>();
        jobRepo.findAll().iterator().forEachRemaining(jobs::add);
        return jobs;
    }

    @Override
    public WorkOrderJob findById(long id) {
        return jobRepo.findById(id).orElseThrow(() -> new DataNotFoundException(String.format(
                "Could not find WorkOrderJob id: %s", id
        )));
    }

    @Override
    public WorkOrderJob saveNew(WorkOrderJob job) {
        WorkOrderJob j = new WorkOrderJob();

        j.setConcern(job.getConcern());
        j.setCause(job.getCause());
        j.setCorrection(job.getCorrection());

        j.setRepairOrder(roService.findById(job.getRepairOrder().getId()));
        return jobRepo.save(j);
    }

    @Override
    public WorkOrderJob saveNew(String concern, long id) {
        WorkOrderJob w = new WorkOrderJob();
        w.setConcern(concern);


        RepairOrder r = roService.findById(id);
        w.setRepairOrder(r);
        return jobRepo.save(w);
    }

    @Override
    public WorkOrderJob update(WorkOrderJob job) {
        WorkOrderJob j = findById(job.getId());

        j.setConcern(job.getConcern());
        j.setCause(job.getCause());
        j.setCorrection(job.getCorrection());

        j.setRepairOrder(roService.findById(job.getRepairOrder().getId()));

        for (WorkOrderJobPart part : job.getParts()) {
            part.setJob(j);
            j.getParts().add(part);
        }

//        for (JobTimePunchSet punchSet : job.getTimeClockPunchSets()) {
//            JobTimePunchSet ps = timeClockService.findJobTimePunchSetById(punchSet.getId());
//            ps.setJob(j);
//            j.getTimeClockPunchSets().add(ps);
//        }

        for (TechnicianFlatRateHour flatRateHour : job.getLabor()) {
            TechnicianFlatRateHour frh = technicianFlatRateHourService.findById(flatRateHour.getId());
            frh.setJob(j);
            j.getLabor().add(frh);
        }

        j.setMiscItems(job.getMiscItems());

        return jobRepo.save(j);
    }

    @Override
    public void delete(long id) {
        findById(id);
        jobRepo.deleteById(id);
    }
}
