package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ServiceMenuItemService serviceMenuItemService;
    private final EmployeeService employeeService;


    public AppointmentController(final AppointmentService appointmentService,
                                 final ServiceMenuItemService serviceMenuItemService,
                                 final EmployeeService employeeService) {
        this.appointmentService = appointmentService;
        this.serviceMenuItemService = serviceMenuItemService;
        this.employeeService = employeeService;
    }

    @GetMapping({"", "/"})
    public String blocks(Principal principal, ModelMap map, @RequestParam(required = false) LocalDate date) {
        map.addAttribute("username", principal.getName());
        if(date == null) date = LocalDate.now();

        List<Employee> advisors = employeeService.getActiveAdvisors();

        List<AppointmentBlock> blocks = appointmentService.findBlockByDate(date, advisors);
        map.addAttribute("blocks", blocks);
        map.addAttribute("date", date);

        map.addAttribute("tblocks", blocks
                        .stream()
                        .filter(b -> b.getAdvisor().getId() == advisors.get(0).getId())
                        .collect(Collectors.toList()));

        String columns = appointmentGridAdvisorColumnStringBuilder(advisors);
        map.addAttribute("columns", columns);
        map.addAttribute("advisors", advisors);

        List<ServiceMenuItem> smis = serviceMenuItemService.findAll();
        map.addAttribute("menu_items", smis);

        List<Appointment> appointments = appointmentService.findAppointmentsByDate(date);
        map.addAttribute("appointments", appointments);

        return "appointments";
    }

    @PostMapping({"/create", "/create/"})
    public String create(Appointment appointment, HttpServletRequest request) {
        appointmentService.saveAppointment(appointment);

        String refURL = request.getHeader("Referer");
        return "redirect:" + refURL;
    }

    @PostMapping({"/delete", "/delete/"})
    public String remove_apt(@RequestParam String appt_id, HttpServletRequest request) {
        long id = Long.parseLong(appt_id);
        appointmentService.deleteAppointment(id);

        String refURL = request.getHeader("Referer");
        return "redirect:" + refURL;
    }

    public String appointmentGridAdvisorColumnStringBuilder(List<Employee> advisors) {
        StringBuilder s = new StringBuilder();
        s.append("grid-template-columns: [times] 5rem ");

        for(int i=0; i < advisors.size(); i++) {
            long id = advisors.get(i).getId();

            if (advisors.size() == 1)  {
                s.append(String.format("[advisor%s] 1fr", id));
            } else if (i == 0) {
                s.append(String.format("[advisor%s-start] 1fr [advisor%s-end", id, id));
            } else if (i == (advisors.size() -1)) {
                s.append(String.format(" advisor%s-start] 1fr [advisor%s-end]", id, id));
            } else {
                s.append(String.format(" advisor%s-start] 1fr [advisor%s-end", id, id));
            }
        }
        s.append(";");
        return s.toString();
    }
}
