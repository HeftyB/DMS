package com.heftyb.dms.appointments;

import com.heftyb.dms.crm.ContactInformation;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImp implements AppointmentService {

    private final AppointmentRepository apptRepo;
    private final AppointmentBlockRepository blockRepo;
    private final EmployeeService employeeService;

    private int defaultInterval = 15;

    public AppointmentServiceImp(final AppointmentRepository appointmentRepository,
                                 final AppointmentBlockRepository appointmentBlockRepository,
                                 final EmployeeService employeeService) {
        apptRepo = appointmentRepository;
        blockRepo = appointmentBlockRepository;
        this.employeeService = employeeService;
    }

    public int getDefaultInterval() {
        return defaultInterval;
    }

    public void setDefaultInterval(int defaultInterval) {
        this.defaultInterval = defaultInterval;
    }

    @Override
    public List<Appointment> findAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        apptRepo.findAll().iterator().forEachRemaining(appointments::add);
        return appointments;
    }

    @Override
    public Appointment findAppointmentById(long id) {
        return apptRepo.findById(id).orElseThrow(() -> new DataNotFoundException(
                String.format("Could not find Appointment id: %s", id)
        ));
    }

    @Override
    public List<Appointment> findAppointmentsByDate(LocalDate date) {
        return findAllAppointments()
                .stream()
                .filter(apt -> apt.getStartDateTime().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByContactInfo(ContactInformation contactInformation) {
        List<Appointment> appointments = new ArrayList<>();
        apptRepo.findAll().iterator().forEachRemaining(appointments::add);
        appointments.stream().filter(a -> a.getContactInformation().containsMatchingContactInformation(contactInformation));

        return appointments;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        Appointment a;

        if (appointment.getId() != 0) {
            a = findAppointmentById(appointment.getId());

            // remove & free any previously held blocks
            List<AppointmentBlock> apb = new ArrayList<>();
            for (AppointmentBlock ap : a.getBlocks()) {
                freeAdvisorBlock(ap);
                apb.add(ap);
            }
            a.getBlocks().removeAll(apb);
        } else {
            a = new Appointment();
        }

        a.setStartDateTime(appointment.getStartDateTime());
        a.setEndDateTime(appointment.getEndDateTime());
        Employee e = employeeService.findById(appointment.getAdvisor().getId());
        a.setAdvisor(e);
        a.setConcerns(appointment.getConcerns());
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(e);


        List<AppointmentBlock> neededBlocks = findBlockByDate(appointment.getStartDateTime().toLocalDate(), employees)
                .stream().filter(block -> (block.getStartDateTime().isAfter(appointment.getStartDateTime()) && block.getEndDateTime().isBefore(appointment.getEndDateTime()) || block.getStartDateTime().isEqual(appointment.getStartDateTime()) || block.getEndDateTime().isEqual(appointment.getEndDateTime())) && block.getAdvisor().getId() == a.getAdvisor().getId())
                .collect(Collectors.toList());


        for (AppointmentBlock ap : neededBlocks) {
            if (!ap.isAvailable()) {
                throw new RuntimeException(String.format("Error: Could not create new appointment, required AppointmentBlock is unavailable: %s", ap));
            }
            ap.setAppointment(a);
            ap.setAvailable(false);
            a.getBlocks().add(ap);
        }

        a.setContactInformation(appointment.getContactInformation());
        a.setConfirmationCode(appointment.getConfirmationCode());

        return apptRepo.save(a);
    }

    @Override
    public void deleteAppointment(long id) {
        Appointment a = findAppointmentById(id);
        a.getBlocks().stream().forEach(this::freeAdvisorBlock);
        apptRepo.deleteById(id);
    }

    @Override
    public List<AppointmentBlock> findAllBlocks() {
        List<AppointmentBlock> blocks = new ArrayList<>();
        blockRepo.findAll().iterator().forEachRemaining(blocks::add);
        return blocks;
    }

    @Override
    public List<AppointmentBlock> findBlockByDate(LocalDate date, List<Employee> advisors) {
        List<AppointmentBlock> blocks = findAllBlocks()
                .stream()
                .filter(b -> b.getStartDateTime().toLocalDate().isEqual(date) && advisors.contains(b.getAdvisor()))
                .collect(Collectors.toList());

        if (blocks.isEmpty()) {
            blocks = createNewBlocksForDate(date, Duration.ofMinutes(defaultInterval), advisors);
        }
        return blocks;
    }

    @Override
    public AppointmentBlock findBlockById(long id) {
        return blockRepo.findById(id).orElseThrow(() -> new DataNotFoundException(
                String.format("Could not find AppointmentBlock id: %s", id)
        ));
    }

    @Override
    public AppointmentBlock saveBlock(AppointmentBlock block) {
        AppointmentBlock b;

        if (block.getId() != 0) {
            b = findBlockById(block.getId());
        } else {
            b = new AppointmentBlock();
        }

        b.setStartDateTime(block.getStartDateTime());
        b.setEndDateTime(block.getEndDateTime());
        b.setAppointment(block.getAppointment());
        b.setAvailable(block.isAvailable());
        b.setDuration(block.getDuration());
        b.setAdvisor(block.getAdvisor());
        return blockRepo.save(b);
    }

    @Override
    public void deleteBlock(long id) {
        findBlockById(id);
        blockRepo.deleteById(id);
    }

    @Override
    public List<AppointmentBlock> createNewBlocksForDate(LocalDate date, Duration duration, List<Employee> advisors) {
        List<AppointmentBlock> daysBlocks = findAllBlocks()
                .stream()
                .filter(b -> b.getStartDateTime().toLocalDate().isEqual(date) && advisors.contains(b.getAdvisor()))
                .collect(Collectors.toList());

        // check for existing advisor blocks
        if (!daysBlocks.isEmpty()) {
            throw new RuntimeException(String.format("Error: Could not create AppointmentBlocks for date: %s, " +
                    "Appointment Blocks already exist please update manually!", date));
        }

        LocalDateTime ldts = LocalDateTime.of(date, LocalTime.parse("08:00:00"));
        LocalDateTime ldte = LocalDateTime.of(date, LocalTime.parse("19:00:00"));

        AppointmentBlock ab = new AppointmentBlock(ldts, duration);
        ab.setStartDateTime(ldts);

        daysBlocks.add(ab);
        AppointmentBlock lastBlock = ab;
        int totalBlocks = calculateBlocks(ldts.toLocalTime(), ldte.toLocalTime(), duration);


        for (int i = 0; i < totalBlocks - 1; i++) {
            AppointmentBlock b = new AppointmentBlock(lastBlock.getEndDateTime(), duration);
            daysBlocks.add(b);
            lastBlock = b;
        }

        List<AppointmentBlock> advisorBlocks = new ArrayList<>();

        for (AppointmentBlock block : daysBlocks) {
            for (Employee ad : advisors) {
                AppointmentBlock appointmentBlock = new AppointmentBlock();
                appointmentBlock.setStartDateTime(block.getStartDateTime());
                appointmentBlock.setEndDateTime(block.getEndDateTime());
                appointmentBlock.setDuration(block.getDuration());
                appointmentBlock.setAdvisor(ad);
                advisorBlocks.add(saveBlock(appointmentBlock));
            }
        }

        return advisorBlocks;
    }

    /**
     * Finds the number of "time blocks" are needed between
     * two LocalTimes with a given Duration
     *
     * @param startTime
     * @param endTime
     * @param apptDuration
     * @return
     */
    private int calculateBlocks(LocalTime startTime, LocalTime endTime, Duration apptDuration) {
        long totalMinutes = Duration.between(startTime, endTime).toMinutes();

        return (int) (totalMinutes / apptDuration.toMinutes());
    }

    /**
     * opens up a previously scheduled appointment block
     *
     * @param block AppointmentBlock to be freed
     */
    private void freeAdvisorBlock(AppointmentBlock block) {
        AppointmentBlock b = findBlockById(block.getId());

        b.setAvailable(true);
        b.setAppointment(null);

        blockRepo.save(b);
    }
}

