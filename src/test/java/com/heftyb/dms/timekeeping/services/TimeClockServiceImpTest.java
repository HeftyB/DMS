package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.ScheduledTasks;
import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.account.repositories.PayPeriodRepository;
import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.repositories.EmployeeRepository;
import com.heftyb.dms.exceptions.TimeClockException;
import com.heftyb.dms.timekeeping.TimeClockPunchSet;
import com.heftyb.dms.timekeeping.TimePunchCode;
import com.heftyb.dms.timekeeping.TimePunchIn;
import com.heftyb.dms.timekeeping.repositories.TimeClockPunchSetRepository;
import com.heftyb.dms.timekeeping.repositories.TimePunchInRepository;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TimeClockServiceImpTest {

    @Autowired
    private TimeClockService timeClockService;

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Autowired
    private TimeClockPunchSetRepository punchSetRepo;

    @Autowired
    private TimePunchInRepository punchInRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PayPeriodRepository payPeriodRepo;

    private PayPeriod currentPeriod;
    private Employee sam;

    @BeforeEach
    void setUp() {
        PayPeriod period = new PayPeriod();
        period.setStartDate(daysFromToday(-3));
        period.setEndDate(daysFromToday(10));
        currentPeriod = payPeriodRepo.save(period);

        sam = employee("sam");
    }

    @Test
    void clockOutClosesTheOpenShift() {
        timeClockService.clockIn("sam", TimePunchCode.IN);

        timeClockService.clockOut("sam", TimePunchCode.OUT);

        assertThat(punchSetRepo.findByEmployee(sam))
                .singleElement()
                .satisfies(shift -> assertThat(shift.getOut().getCode()).isEqualTo(TimePunchCode.OUT));
        assertThat(sam.isClockedIn()).isFalse();
    }

    @Test
    void clockOutWithoutAnOpenShiftIsRejected() {
        assertThatThrownBy(() -> timeClockService.clockOut("sam", TimePunchCode.OUT))
                .isInstanceOf(TimeClockException.class);
    }

    @Test
    void midnightJobClosesShiftsThatStartedTheDayBefore() {
        TimeClockPunchSet shift = openShift(sam, LocalDate.now().minusDays(1).atTime(22, 0), currentPeriod);

        scheduledTasks.clockAllUsersOut();

        assertThat(punchSetRepo.findById(shift.getId()))
                .get()
                .satisfies(s -> assertThat(s.getOut().getCode()).isEqualTo(TimePunchCode.SYSTEM));
    }

    @Test
    void midnightJobKeepsGoingWhenOneShiftCannotBeClosed() {
        // A shift with no pay period cannot be saved again, so closing it fails
        openShift(employee("broken"), LocalDate.now().minusDays(1).atTime(21, 0), null);
        TimeClockPunchSet shift = openShift(sam, LocalDate.now().minusDays(1).atTime(22, 0), currentPeriod);

        scheduledTasks.clockAllUsersOut();

        assertThat(punchSetRepo.findById(shift.getId()))
                .get()
                .satisfies(s -> assertThat(s.getOut()).isNotNull());
    }

    private TimeClockPunchSet openShift(Employee employee, LocalDateTime start, PayPeriod period) {
        TimePunchIn in = punchInRepo.save(new TimePunchIn(employee, start, TimePunchCode.IN));
        return punchSetRepo.save(new TimeClockPunchSet(start.toLocalDate(), in, employee, period));
    }

    private Employee employee(String username) {
        Employee e = new Employee();
        e.setFirstName(username);
        e.setLastName("Tester");
        e.setPreferredName(username);
        e = employeeRepo.save(e);

        User user = userRepo.save(new User(username, "unused", username + "@example.com", e));
        e.setUser(user);
        return e;
    }

    private static Date daysFromToday(int days) {
        return Date.from(LocalDate.now().plusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
