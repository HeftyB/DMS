package com.heftyb.dms.repairorder.controllers;

import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.WorkOrderJob;
import com.heftyb.dms.repairorder.WorkOrderStatus;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import com.heftyb.dms.repairorder.services.WorkOrderJobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("two")
public class TechnicianWOController {


    private final WorkOrderJobService workOrderJobService;
    private final RepairOrderService repairOrderService;

    public TechnicianWOController (final WorkOrderJobService workOrderJobService,
                                   final RepairOrderService repairOrderService) {
        this.workOrderJobService = workOrderJobService;
        this.repairOrderService = repairOrderService;
    }

    @GetMapping({"/", ""})
    public String two(Principal principal, ModelMap map) {
        map.addAttribute("username", principal.getName());

        return "two";
    }

    @GetMapping({"/wo", "/wo/"})
    public String workOrderDetail(Principal principal, ModelMap map, @RequestParam String woId) {
        RepairOrder ro = repairOrderService.findById(Long.parseLong(woId));
        map.addAttribute("username", principal.getName());
        map.addAttribute("wo", ro);
        return "technician_workorder";
    }


    @GetMapping({"/job", "/job/"})
    public String jobDetail(Principal principal, ModelMap map, @RequestParam String jobId) {
        map.addAttribute("username", principal.getName());
        WorkOrderJob job = workOrderJobService.findById(Long.parseLong(jobId));
        map.addAttribute("job", job);


        return "technician_job_detail";
    }

    @PostMapping({"/update", "/update/"})
    public String update(WorkOrderJob job) {
        workOrderJobService.update(job);

        return String.format("redirect:/two/job?jobId=%s", job.getId());
    }

    @PostMapping({"/cause", "/cause/"})
    public String updateCause(@RequestParam String cause, @RequestParam String jobId) {
        long id = Long.parseLong(jobId);
        workOrderJobService.updateCause(id, cause);

        return retString(id);
    }

    @PostMapping({"/correction", "/correction/"})
    public String updateCorrection(@RequestParam String correction, @RequestParam String jobId) {
        long id = Long.parseLong(jobId);
        workOrderJobService.updateCorrection(id, correction);

        return retString(id);
    }

    @PostMapping({"/status", "/status/"})
    public String updateStatus(@RequestParam String status, @RequestParam String jobId) {
        long id = Long.parseLong(jobId);
        workOrderJobService.updateStatus(id, WorkOrderStatus.valueOf(status));

        return retString(id);
    }

    private String retString(long id) {
        return String.format("redirect:/two/job?jobId=%s", id);
    }
}
