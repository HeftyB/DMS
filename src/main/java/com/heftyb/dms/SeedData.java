package com.heftyb.dms;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.account.services.PayPeriodService;
import com.heftyb.dms.crm.*;
import com.heftyb.dms.crm.services.ContactService;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.RepairOrderJob;
import com.heftyb.dms.repairorder.services.RepairOrderJobService;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import com.heftyb.dms.timekeeping.TimePunchCode;
import com.heftyb.dms.timekeeping.services.TimeClockService;
import com.heftyb.dms.users.Role;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.UserDTO;
import com.heftyb.dms.users.UserRole;
import com.heftyb.dms.users.services.RoleService;
import com.heftyb.dms.users.services.UserService;
import com.heftyb.dms.vehicles.Manufacturer;
import com.heftyb.dms.vehicles.Model;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.ManufacturerService;
import com.heftyb.dms.vehicles.services.ModelService;
import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Transactional
@Component
public class SeedData implements CommandLineRunner {

    private final ManufacturerService manufacturerService;
    private final VehicleService vehicleService;
    private final ModelService modelService;
    private final ContactService contactService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final RepairOrderService repairOrderService;
    private final RepairOrderJobService jobService;
    private final UserService userService;
    private final RoleService roleService;
    private final TimeClockService timeClockService;
    private final PayPeriodService payPeriodService;

    public SeedData(final ManufacturerService manufacturerService,
                    final VehicleService vehicleService,
                    final ModelService modelService,
                    final ContactService contactService,
                    final CustomerService customerService,
                    final RepairOrderService repairOrderService,
                    final RepairOrderJobService jobService,
                    final EmployeeService employeeService,
                    final UserService userService,
                    final RoleService roleService,
                    final TimeClockService timeClockService,
                    final PayPeriodService payPeriodService) {
        this.manufacturerService = manufacturerService;
        this.vehicleService = vehicleService;
        this.modelService = modelService;
        this.contactService = contactService;
        this.customerService = customerService;
        this.repairOrderService = repairOrderService;
        this.employeeService = employeeService;
        this.jobService = jobService;
        this.userService = userService;
        this.roleService = roleService;
        this.timeClockService = timeClockService;
        this.payPeriodService = payPeriodService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        System.out.println("---------------------**STARTING**---------------------");


        Map<String, String[]> map = Map.ofEntries(
                new AbstractMap.SimpleEntry<>("ABARTH", new String[]{"204", "205", "750", "850", "1000", "1150", "1300", "1600", "2000", "SIMCA"}),
                new AbstractMap.SimpleEntry<>("ACURA", new String[]{"MDX", "NSX", "RL", "RSX", "TL", "TSX", "CSX", "RDX", "ZDX", "ILX", "RLX", "TLX", "INTEGRA"}),
                new AbstractMap.SimpleEntry<>("ALFA ROMEO", new String[]{"8C", "4C", "GIULIA", "STELVIO", "TONALE"}),
                new AbstractMap.SimpleEntry<>("ASTON MARTIN", new String[]{"DB7", "VANQUISH", "DB9", "V8 VANTAGE", "V12 VANTAGE", "DBS", "RAPIDE", "ONE-77", "VIRAGE", "VANTAGE", "RAPIDE E", "DB11", "DBX", "DBX707", "VALOUR"}),
                new AbstractMap.SimpleEntry<>("AUDI", new String[]{"A3", "A4", "A5", "A6", "A7", "A8", "ALLROAD", "S4", "S6", "TT", "Q7", "RS4", "RS5", "RS7", "S8", "R8", "S5", "Q5", "S7", "SQ5", "Q3", "S3", "RS3", "ETRON", "Q8", "RSQ8", "SQ7", "SQ8", "Q4 ETRON", "RS3", "RS5", "RS6"}),
                new AbstractMap.SimpleEntry<>("BENTLY", new String[]{"ARNAGE", "CONTINENTAL", "AZURE", "BROOKLANDS", "MULSANNE", "FLYING SPUR", "BENTAYGA"}),
                new AbstractMap.SimpleEntry<>("BMW", new String[]{"325", "328", "330", "335", "525", "530", "545", "645", "745", "750", "760", "M1", "M2", "M3", "M4", "M5", "M6", "XM", "X1", "X2", "X3", "X4", "X5", "X6", "X7", "Z3", "Z4", "Z8", "i3", "i4", "i5", "i7", "iX1", "iX2", "iX3", "ix"}),
                new AbstractMap.SimpleEntry<>("INFINITI", new String[]{"Q50", " QX50", "QX55", "QX60", "QX80", "QX56", "G35", "MX30", "I-SERIES", "J-SERIES"})

//                new AbstractMap.SimpleEntry<String, String[]>("ABARTH", new String[]{"", ""})

        );


        map.forEach((k, v) -> {
            Manufacturer m = new Manufacturer(k);
            m = manufacturerService.save(m);
            m.setModels(new ArrayList<>());
            for (String s : v) {
                m.getModels().add(modelService.save(new Model(s, m)));
            }
        });


        List<Manufacturer> manufacturers = manufacturerService.findAll();

        MailingAddress mailingAddress = new MailingAddress("HeftyB", "123 test ln", "", "Jacksonville", "Florida", "37770");
        MailingAddress mailingAddress1 = new MailingAddress("DanaD", "654 fast dr", "", "Jacksonville", "Florida", "37770");


        PhoneNumber phoneNumber = new PhoneNumber("9045555555", true, PhoneNumberType.HOME);
        PhoneNumber phoneNumber1 = new PhoneNumber("9045555432", true, PhoneNumberType.HOME);
        PhoneNumber phoneNumber2 = new PhoneNumber("9045555431", true, PhoneNumberType.HOME);
        PhoneNumber phoneNumber3 = new PhoneNumber("9045555455", true, PhoneNumberType.HOME);
        PhoneNumber phoneNumber4 = new PhoneNumber("9045555955", true, PhoneNumberType.HOME);


        Employee e = new Employee("Hefty", "Burrito", "Hefty", mailingAddress, "5555555", JobTitle.GENERAL_MANAGER);
        phoneNumber1.setEmployee(e);
        e.getPhoneNumbers().add(phoneNumber1);
        e = employeeService.save(e);

        Employee e1 = new Employee("Hefty", "Taco", "Hefty", mailingAddress, "5555551", JobTitle.ACCOUNT_MANAGER);
        phoneNumber2.setEmployee(e1);
        e1.getPhoneNumbers().add(phoneNumber2);
        e1 = employeeService.save(e1);

        Employee e2 = new Employee("Hefty", "System", "Hefty", mailingAddress, "5555559", JobTitle.ACCOUNT_MANAGER);
        phoneNumber3.setEmployee(e2);
        e2.getPhoneNumbers().add(phoneNumber3);
        e2 = employeeService.save(e2);

        Employee e3 = new Employee("Hefty", "User", "Hefty", mailingAddress, "5555550", JobTitle.PORTER);
        phoneNumber4.setEmployee(e3);
        e3.getPhoneNumbers().add(phoneNumber4);
        e3 = employeeService.save(e3);


        Customer customer = new Customer("Dana", "Dee", "maiL@mail.com");
        customer.setMailingAddress(mailingAddress1);

        phoneNumber.setCustomer(customer);
        customer.getPhoneNumbers().add(phoneNumber);

        customer = customerService.save(customer);

        Manufacturer manufacturer = manufacturerService.findByName("INFINITI");
        Model model = manufacturer.getModels().stream().filter((m) -> m.getName() == "QX60").findFirst().orElseThrow(
                () -> new DataNotFoundException("couldn't find model QX60")
        );
        String vin = "5N1DL0MN0LC520454";
        Vehicle vehicle = new Vehicle(vin, 2020, manufacturer, model);

        vehicle.setCustomer(customer);

        vehicle = vehicleService.save(vehicle);


//        RepairOrder r = new RepairOrder(Date.from(Instant.now()), customer, vehicle, 87088, "1", e);

        RepairOrder r = new RepairOrder();
        r.setOpenDate(Date.from(Instant.now()));
        r.setCustomer(customer);
        r.setVehicle(vehicle);
        r.setMileageIn(87088);
        r.setServiceTag("18765");
        r.setAdvisor(e);
        r.setActive(true);


//        System.out.println(r);

        r = repairOrderService.save(r);
        RepairOrderJob[] jobs = {
                jobService.save(new RepairOrderJob(r, "REPLACE FRONT BRAKE PADS AND ROTORS, CUSTOMER SUPPLIED PARTS")),
                jobService.save(new RepairOrderJob(r, "REPLACE REAR BRAKE PADS AND ROTORS, CUSTOMER SUPPLIED PARTS")),
                jobService.save(new RepairOrderJob(r, "BRAKE FLUID FLUSH, CUSTOMER SUPPLIED PARTS"))
        };

        for (RepairOrderJob j : jobs) {
            r.getJobs().add(j);
        }

//        r = repairOrderService.save(r);
        Role r1 = new Role("ADMIN");
        Role r2 = new Role("STAFF");
        Role r3 = new Role("USER");
        Role r4 = new Role("SYSTEM");
        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);
        r4 = roleService.save(r4);

        UserDTO u = new UserDTO();
        u.setUsername("hefty");
        u.setPassword("burrito");
        u.setMatchingPassword("burrito");
        u.setEmail("heftyb@heftyb.com");
        u.setEmployee(e);
        User user = userService.registerNewUserAccount(u);

        user.getRoles().add(new UserRole(user, roleService.findByRole("ADMIN")));
        user = userService.saveRegisteredUser(user);
        System.out.println(String.format("newUser: \n %s", user));

        UserDTO u1 = new UserDTO();
        u1.setUsername("taco");
        u1.setPassword("taco");
        u1.setMatchingPassword("taco");
        u1.setEmail("heftytaco@heftyb.com");
        u1.setEmployee(e1);
        User user1 = userService.registerNewUserAccount(u1);

        UserDTO u2 = new UserDTO();
        u2.setUsername("system");
        u2.setPassword("system");
        u2.setMatchingPassword("system");
        u2.setEmail("heftysystem@heftyb.com");
        u2.setEmployee(e2);
        User user2 = userService.registerNewUserAccount(u2);

        UserDTO u3 = new UserDTO();
        u3.setUsername("user");
        u3.setPassword("user");
        u3.setMatchingPassword("user");
        u3.setEmail("heftyuser@heftyb.com");
        u3.setEmployee(e3);
        User user3 = userService.registerNewUserAccount(u3);

        user1.getRoles().add(new UserRole(user1, roleService.findByRole("STAFF")));
        user1 = userService.saveRegisteredUser(user1);
        System.out.println(String.format("newUser1: \n %s", user1));

        user2.getRoles().add(new UserRole(user2, roleService.findByRole("SYSTEM")));
        user2 = userService.saveRegisteredUser(user2);
        System.out.println(String.format("newUser2: \n %s", user2));
        System.out.println(String.format("newUser3: \n %s", user3));
        System.out.println(String.format("newUser3: \n %s", user3));


        String payStartDate = "2024-08-18";
        String payEndDate = "2024-08-31";

        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

        newPayPeriodFromString(payStartDate, payEndDate, sdf);


        String payStartDate4 = "2024-09-01";
        String payEndDate4 = "2024-09-14";
        newPayPeriodFromString(payStartDate4, payEndDate4, sdf);


        String payStartDate1 = "2024-08-04";
        String payEndDate1 = "2024-08-17";


        newPayPeriodFromString(payStartDate1, payEndDate1, sdf);


        String payStartDate2 = "2024-07-21";
        String payEndDate2 = "2024-08-03";


        newPayPeriodFromString(payStartDate2, payEndDate2, sdf);


        payPeriodService.getNextPayPeriod();


        timeClockService.clockIn(user.getUsername(), TimePunchCode.IN);
        timeClockService.clockOut(user.getUsername(), TimePunchCode.OUT);

        timeClockService.clockIn(user.getUsername(), TimePunchCode.LUNCH);


        System.out.println(payPeriodService.findAll().size());


        FakeValuesService fakeValuesService = new FakeValuesService(Locale.getDefault(), new RandomService());
        Faker faker = new Faker(Locale.getDefault());

        String vinReg = "[A-HJ-NPR-Z0-9]{17}";

        for (int i = 0; i < 100; i++) {
            String fname = faker.name().firstName();
            String lname = faker.name().lastName();
            String addNumber = faker.address().buildingNumber();
            String street = faker.address().streetName();
            String city = faker.address().city();
            String state = faker.address().state();
            String zip = faker.address().zipCode();
            String email = faker.internet().emailAddress();
            String phone = faker.phoneNumber().phoneNumber();

            PhoneNumber newPhone = new PhoneNumber(phone, true, PhoneNumberType.HOME);
            MailingAddress newAddress = new MailingAddress(fname + " " + lname, addNumber + " " + street, "", city, state, zip);
            Customer newCustomer = new Customer(fname, lname, email);

            newAddress.setCustomer(newCustomer);
            newCustomer.setMailingAddress(newAddress);

            newPhone.setCustomer(newCustomer);
            newCustomer.addPhone(newPhone);

            newCustomer = customerService.save(newCustomer);

            Manufacturer newManufacturer = manufacturerService.save(new Manufacturer(faker.company().name()));
            Model newModel = modelService.save(new Model(faker.rockBand().name(), newManufacturer));
            newManufacturer.getModels().add(newModel);

            LocalDate sr = LocalDate.of(1965, Month.JANUARY, 1);
            LocalDate se = LocalDate.of(2025, Month.JANUARY, 1);

            LocalDate vehicleManDate = between(sr, se);


            Vehicle newVehicle = new Vehicle(faker.regexify(vinReg), vehicleManDate.getYear(), newManufacturer, newModel);
            newVehicle.setCustomer(newCustomer);
            newVehicle = vehicleService.save(newVehicle);
        }


        System.out.println("---------------------**FINISHED!!!!**---------------------");

    }

    private void newPayPeriodFromString(String payStartDate, String payEndDate, SimpleDateFormat sdf) throws ParseException {
        Date startDate = sdf.parse(payStartDate);
        Date endDate = sdf.parse(payEndDate);

        PayPeriod payPeriod = new PayPeriod();

        payPeriod.setPeriod(Period.ofWeeks(2));
        payPeriod.setStartDate(startDate);
        payPeriod.setEndDate(endDate);

        payPeriodService.save(payPeriod);
    }

    public LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }
}
