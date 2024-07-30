package com.heftyb.dms.repairorder.services;

import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.MiscellaneousItem;
import com.heftyb.dms.repairorder.RepairOrderJob;
import com.heftyb.dms.repairorder.repositories.RepairOrderJobRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "repairOrderJobService")
public class RepairOrderJobServiceImp implements RepairOrderJobService{

    final private RepairOrderJobRepository jobRepo;
    final private RepairOrderService repairOrderService;
    final private MiscellaneousItemsService miscellaneousItemsService;

    public RepairOrderJobServiceImp(final RepairOrderJobRepository jobRepo,
                                    final RepairOrderService repairOrderService,
                                    final MiscellaneousItemsService miscellaneousItemsService) {
        this.jobRepo = jobRepo;
        this.repairOrderService = repairOrderService;
        this.miscellaneousItemsService = miscellaneousItemsService;
    }

    @Override
    public List<RepairOrderJob> findAll() {
        List<RepairOrderJob> jobs = new ArrayList<>();
        jobRepo.findAll().iterator().forEachRemaining(jobs::add);
        return jobs;
    }

    @Override
    public RepairOrderJob findById(long id) {
        return jobRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "RepairOrderJobService Error: could not find repairOrderJob id %g", id
                ))
        );
    }

    @Override
    public RepairOrderJob save(RepairOrderJob repairOrderJob) {
        RepairOrderJob r = new RepairOrderJob();
        r.setRepairOrder(repairOrderService.findById(repairOrderJob.getRepairOrder().getId()));
        r.setConcern(repairOrderJob.getConcern());
        r.setCause(repairOrderJob.getCause());
        r.setCorrection(repairOrderJob.getCorrection());
        r.setParts(new ArrayList<>());
        r.setTimeClockPunchSets(new ArrayList<>());
        r.setLabor(new ArrayList<>());
        r.setMiscItems(new ArrayList<>());

//        for (MiscellaneousItem item : repairOrderJob.getMiscItems()) {
//            MiscellaneousItem m = miscellaneousItemsService.findById(item.getId());
//            r.getMiscItems().add(m);
//        }

        return jobRepo.save(r);
    }

    @Override
    public void delete(long id) {
        findById(id);
        jobRepo.deleteById(id);
    }
}
