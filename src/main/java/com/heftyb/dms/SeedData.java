package com.heftyb.dms;

import com.heftyb.dms.account.PayPeriod;
import com.heftyb.dms.account.services.PayPeriodService;
import com.heftyb.dms.appointments.ServiceMenuItem;
import com.heftyb.dms.appointments.ServiceMenuItemService;
import com.heftyb.dms.crm.*;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.WorkOrderJob;
import com.heftyb.dms.repairorder.WorkOrderStatus;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import com.heftyb.dms.timekeeping.TimePunchCode;
import com.heftyb.dms.timekeeping.services.TimeClockService;
import com.heftyb.dms.users.Role;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.UserDTO;
import com.heftyb.dms.users.UserRole;
import com.heftyb.dms.users.services.RoleService;
import com.heftyb.dms.users.services.UserService;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * First-run data for a fresh database: roles, users (including an admin), employees, the service
 * menu, and a handful of customers with vehicles decoded through the NHTSA API.
 *
 * <p>Runs only under the "seed" profile:
 * {@code ./mvnw spring-boot:run -Dspring-boot.run.profiles=seed}
 */
@Transactional
@Component
@Profile("seed")
public class SeedData implements CommandLineRunner {

    private final VehicleService vehicleService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final RepairOrderService repairOrderService;
    private final UserService userService;
    private final RoleService roleService;
    private final TimeClockService timeClockService;
    private final PayPeriodService payPeriodService;
    private final ServiceMenuItemService serviceMenuItemService;

    // Sample values, rotated by sampleIndex, so a seeded database looks the same every time.
    private static final String[] FIRST_NAMES = {"Dana", "Marcus", "Priya", "Luis", "Nina", "Omar",
            "Grace", "Tomas", "Rae", "Ibrahim", "Sofia", "Wes"};
    private static final String[] LAST_NAMES = {"Reyes", "Okafor", "Whitfield", "Nakamura", "Delgado",
            "Brennan", "Osei", "Calderon", "Vance", "Petrov", "Alvarez", "Boone"};
    private static final String[] STREETS = {"Main St", "Atlantic Blvd", "Hendricks Ave", "Beach Blvd",
            "San Marco Blvd", "Roosevelt Blvd"};
    private static final String[] CITIES = {"Jacksonville", "Orange Park", "Neptune Beach", "St. Augustine"};
    private static final String[] ZIPS = {"32202", "32073", "32266", "32084"};

    // Each seeded vehicle is decoded through the live NHTSA API, so only the first few of the
    // VINs below are used. Raise this to seed more.
    private static final int SEEDED_VEHICLES = 12;

    private int sampleIndex = 0;

    private String vinReg = "[A-HJ-NPR-Z0-9]{17}";

    public SeedData(final VehicleService vehicleService,
                    final CustomerService customerService,
                    final RepairOrderService repairOrderService,
                    final EmployeeService employeeService,
                    final UserService userService,
                    final RoleService roleService,
                    final TimeClockService timeClockService,
                    final PayPeriodService payPeriodService,
                    final ServiceMenuItemService serviceMenuItemService) {
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.repairOrderService = repairOrderService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.roleService = roleService;
        this.timeClockService = timeClockService;
        this.payPeriodService = payPeriodService;
        this.serviceMenuItemService = serviceMenuItemService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        System.out.println("---------------------**STARTING SEED OF TEST DATA**---------------------");
//
//
//        Map<String, String[]> map = Map.ofEntries(
//                new AbstractMap.SimpleEntry<>("ABARTH", new String[]{"204", "205", "750", "850", "1000", "1150", "1300", "1600", "2000", "SIMCA"}),
//                new AbstractMap.SimpleEntry<>("ACURA", new String[]{"MDX", "NSX", "RL", "RSX", "TL", "TSX", "CSX", "RDX", "ZDX", "ILX", "RLX", "TLX", "INTEGRA"}),
//                new AbstractMap.SimpleEntry<>("ALFA ROMEO", new String[]{"8C", "4C", "GIULIA", "STELVIO", "TONALE"}),
//                new AbstractMap.SimpleEntry<>("ASTON MARTIN", new String[]{"DB7", "VANQUISH", "DB9", "V8 VANTAGE", "V12 VANTAGE", "DBS", "RAPIDE", "ONE-77", "VIRAGE", "VANTAGE", "RAPIDE E", "DB11", "DBX", "DBX707", "VALOUR"}),
//                new AbstractMap.SimpleEntry<>("AUDI", new String[]{"A3", "A4", "A5", "A6", "A7", "A8", "ALLROAD", "S4", "S6", "TT", "Q7", "RS4", "RS5", "RS7", "S8", "R8", "S5", "Q5", "S7", "SQ5", "Q3", "S3", "RS3", "ETRON", "Q8", "RSQ8", "SQ7", "SQ8", "Q4 ETRON", "RS3", "RS5", "RS6"}),
//                new AbstractMap.SimpleEntry<>("BENTLY", new String[]{"ARNAGE", "CONTINENTAL", "AZURE", "BROOKLANDS", "MULSANNE", "FLYING SPUR", "BENTAYGA"}),
//                new AbstractMap.SimpleEntry<>("BMW", new String[]{"325", "328", "330", "335", "525", "530", "545", "645", "745", "750", "760", "M1", "M2", "M3", "M4", "M5", "M6", "XM", "X1", "X2", "X3", "X4", "X5", "X6", "X7", "Z3", "Z4", "Z8", "i3", "i4", "i5", "i7", "iX1", "iX2", "iX3", "ix"}),
//                new AbstractMap.SimpleEntry<>("INFINITI", new String[]{"Q50", " QX50", "QX55", "QX60", "QX80", "QX56", "G35", "MX30", "I-SERIES", "J-SERIES"})

//                new AbstractMap.SimpleEntry<String, String[]>("ABARTH", new String[]{"", ""})

//        );

//
//        map.forEach((k, v) -> {
//            Manufacturer m = new Manufacturer(k);
//            m = manufacturerService.save(m);
//            m.setModels(new ArrayList<>());
//            for (String s : v) {
//                Model model = new Model();
//                model.setName(s);
//                m.getModels().add(model);
//            }
//        });



        String[] vins = {
                "1J8HS58PX7C635448",
                "1G2ZG57B184299126",
                "5XYZG3AB0BG040600",
                "1G6DV1EP4E0170211",
                "2LMDJ6JK5CBL05174",
                "1G2ZH35N074252067",
                "WAULFAFH2EN000975",
                "2FUYDDYB2VA851130",
                "1NXBR32E08Z970505",
                "1N4AL3AP7DN416134",
                "1J4FF68S1XL674047",
                "1C3CCBAB5EN141905",
                "3VWDP7AJ4CM332499",
                "SALTL19414A830740",
                "1ZVHT84N485154897",
                "JTJBC1BA8C2433180",
                "4T1SK13E7SU578278",
                "JA4LS31H9YP816571",
                "1GCJC33D97F131320",
                "19UUA8F22CA009000",
                "2T2ZK1BA0DC103617",
                "1J4FY19S9WP775548",
                "4M2CN9HG1AKJ23522",
                "1FMHK8F89BGA70952",
                "WDDGJ4HB7CF772253",
                "1G6AB5RX9D0155845",
                "1C3CDZAB6DN653600",
                "1G6DW6ED9B0122540",
                "WDBDA24D2GF259814",
                "YV4992DZ0A2051468",
                "1G1AK55F767863024",
                "1HGEJ6574WL019171",
                "1ZVFT80NX55155867",
                "WDBRF52H77F935821",
                "1HGCP26338A026928",
                "1G1PC5SB9E7299772",
                "JTMDF4DV4A5025081",
                "3C6JR6AT0DG508290",
                "1C4RDJAG1FC729886",
                "1B7HF13Z5XJ632482",
                "1HGCM66553A009137",
                "JA3AU26U89U013937",
                "JHMZE2H73DS000086",
                "1G4CU5211X4610086",
                "1N4AL3AP4FN363413",
                "1FMYU24X1WUA90931",
                "4T1BD1FK8CU046695",
                "1FTNE2EL6DDA18281",
                "2HSFHAER3SC020956",
                "WP0CA298X6U712970",
                "WAUSGAFC9CN008632",
                "1FTWW31P96EC05821",
                "JM1BL1L72D1766133",
                "KNAGM4A72E5469779",
                "2G1WC5E36D1171047",
                "4A3AK34T86E011731",
                "5N1AN0NW7AC504625",
                "JHLRE38318C000286",
                "1G1ZH57B98F200400",
                "1C4BJWFG5EL230880",
                "JTLZE4FEXCJ024974",
                "5N1AR2MM6DC688542",
                "1B7GG22N91S271790",
                "1C4RJFCG2CC219089",
                "1FAHP3FN8AW180450",
                "JTKKU4B41AJ056876",
                "1HGCR3F89DA047983",
                "5FNYF4H29BB014059",
                "1N4AL24E99C123410",
                "1G4HE5EM6AU109002",
                "WBA3A5G58DNP24775",
                "1N4AA5AP3DC830377",
                "1FTNE1EW9EDA77217",
                "JM3KE2BE7F0554084",
                "1FTRW12W38FB97218",
                "1HGCP2F87BA039104",
                "JN8AS5MT2AW007471",
                "1N4AL24E69C180938",
                "3VWRA69M84M071783",
                "JN8AS58T89W040447",
                "1FDWE37SXWHB57450",
                "YV4902BZ5C1127952",
                "1G4GB5EG0AF164354",
                "5TFJX4GN0FX040846",
                "3GNDA23DX7S523478",
                "1FMFU17527LA73039",
                "1FTNW21P94EC52558",
                "2C3CCAEG2DH606115",
                "WDBRF64J31F094917",
                "19XFB2F80EE272334",
                "1G1ZC5EU0CF117675",
                "3N1AB41D7XL113306",
                "KNDJT2A22B7707425",
                "3GNEC13T63G129215",
                "4T1BE46KX9U866901",
                "1G2ZM151X64128285",
                "5GAEV23D79J176540",
                "1FALP52U0TA316234",
                "1N4AL11D23C233714",
                "1HGCS228X9A010002",
                "JF1GG61697H804055",
                "3FAHP0JG2CR274563",
                "5TDZK22C99S267314",
                "1GKEC16Z55J253782",
                "1GNDS13S472120727",
                "KNADE123986307771",
                "4NUDS13S452704023",
                "YV1MS390762193044",
                "2A4GP54L57R230672",
                "1FTRX17W12NB66858",
                "JM3TB28A880138744",
                "1G6AR5SX4E0176240",
                "1FMJU1J50BEF12707",
                "2HNYD18635H550294",
                "3GYEK62N55G138416",
                "2FMDK3J99DBA04462",
                "JA3AJ86E03U081936",
                "1HGCS22828A003138",
                "1C4AJWAG4EL226290",
                "KMHCN4AC4BU618590",
                "1FM5K8F8XDGA60078",
                "1G6DT57V690108484",
                "1G1PH5SB6D7109328",
                "16DJ631F211191304",
                "1N6AD0EVXEN725187",
                "JM1CW2BL8C0119675",
                "2CNDL13F786305332",
                "1FM5K7F81EGC01552",
                "3N1AB7AP2DL634571",
                "JF2GPAPC1F8210313",
                "1LNHM83WX3Y637842",
                "1FTYR14V91PB71438",
                "5NPEC4AB0DH559237",
                "1GKS2CKJ2FR147312",
                "5NPEU46F87H156524",
                "JTHBA30G745014991"
        };


        Employee e = new Employee("Hefty", "Burrito", "Hefty", randomAddress(),
                randomContactInfo(),"5555555", JobTitle.GENERAL_MANAGER, LocalDateTime.now());

        Employee e1 = new Employee("Hefty", "Taco", "Taco", randomAddress(),
                randomContactInfo(),"5555551", JobTitle.ACCOUNT_MANAGER, LocalDateTime.now());

        Employee e2 = new Employee("Hefty", "System", "System", randomAddress(),
                randomContactInfo(),"5555554", JobTitle.OFFICE_ADMIN, LocalDateTime.now());
        Employee e3 = new Employee("Hefty", "User", "User", randomAddress(),
                randomContactInfo(),"5555552", JobTitle.PORTER, LocalDateTime.now());


        Employee e4 = new Employee("Hefty", "Advisor", "Hefty", randomAddress(),
                randomContactInfo(),"55555599", JobTitle.SERVICE_WRITER, LocalDateTime.now());

        Employee e5 = new Employee("Hefty", "Service Writer", "Hefty", randomAddress(),
                randomContactInfo(),"55555599", JobTitle.SERVICE_WRITER, LocalDateTime.now());


        e = employeeService.save(e);
        e1 = employeeService.save(e1);
        e2 = employeeService.save(e2);
        e3 = employeeService.save(e3);
        e4 = employeeService.save(e4);
        e5 = employeeService.save(e5);



        Customer customer = new Customer("Dana", "Dee", randomAddress(), randomContactInfo(), "maiL@mail.com");


        customer = customerService.saveNewCustomer(customer);


        String vin = "5J6YH18203L001377";





        Vehicle vehicle = vehicleService.decodeVIN(vin);

        vehicle.setCustomer(customer);

        vehicle = vehicleService.saveNew(vehicle);


        RepairOrder r = new RepairOrder();
        r.setOpenDate(Date.from(Instant.now()));
        r.setStatus(WorkOrderStatus.ENTERED);
        r.setCustomer(customer);
        r.setVehicle(vehicle);
        r.setMileageIn(87088);
        r.setServiceTag("18765");
        r.setAdvisor(e);
        r.setActive(true);


        r = repairOrderService.save(r);
        WorkOrderJob[] jobs = {
                new WorkOrderJob("REPLACE FRONT BRAKE PADS AND ROTORS, CUSTOMER SUPPLIED PARTS"),
                new WorkOrderJob("REPLACE REAR BRAKE PADS AND ROTORS, CUSTOMER SUPPLIED PARTS"),
                new WorkOrderJob("PERFORM BRAKE FLUID FLUSH, CUSTOMER SUPPLIED PARTS")
        };

        for (WorkOrderJob j : jobs) {
            j.setRepairOrder(r);
            r.getJobs().add(j);
        }

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

        UserDTO u4 = new UserDTO();
        u4.setUsername("advisor");
        u4.setPassword("advisor");
        u4.setMatchingPassword("advisor");
        u4.setEmail("advisor@heftyb.com");
        u4.setEmployee(e4);
        User user4 = userService.registerNewUserAccount(u4);

        user4.getRoles().add(new UserRole(user4, roleService.findByRole("USER")));
        user4 = userService.saveRegisteredUser(user4);

        UserDTO u5 = new UserDTO();
        u5.setUsername("writer");
        u5.setPassword("writer");
        u5.setMatchingPassword("writer");
        u5.setEmail("writer@heftyb.com");
        u5.setEmployee(e5);
        User user5 = userService.registerNewUserAccount(u5);

        user5.getRoles().add(new UserRole(user5, roleService.findByRole("USER")));
        user5 = userService.saveRegisteredUser(user5);




        UserDTO u1 = new UserDTO();
        u1.setUsername("taco");
        u1.setPassword("taco");
        u1.setMatchingPassword("taco");
        u1.setEmail("heftytaco@b.com");
        u1.setEmployee(e1);
        User user1 = userService.registerNewUserAccount(u1);

        UserDTO u2 = new UserDTO();
        u2.setUsername("system");
        u2.setPassword("system");
        u2.setMatchingPassword("system");
        u2.setEmail("heftysystem@b.com");
        u2.setEmployee(e2);
        User user2 = userService.registerNewUserAccount(u2);

        UserDTO u3 = new UserDTO();
        u3.setUsername("user");
        u3.setPassword("user");
        u3.setMatchingPassword("user");
        u3.setEmail("heftyuser@b.com");
        u3.setEmployee(e3);
        User user3 = userService.registerNewUserAccount(u3);

        user1.getRoles().add(new UserRole(user1, roleService.findByRole("STAFF")));
        user1 = userService.saveRegisteredUser(user1);

        user2.getRoles().add(new UserRole(user2, roleService.findByRole("SYSTEM")));
        user2 = userService.saveRegisteredUser(user2);


        // Six two-week pay periods ending with the one that covers today, so seeded timekeeping
        // is current whenever the seed runs. Dates were fixed 2024 strings before.
        LocalDate currentPeriodStart = LocalDate.now().minusDays(6);
        for (int period = 5; period >= 0; period--) {
            LocalDate start = currentPeriodStart.minusWeeks(2L * period);
            newPayPeriod(start, start.plusDays(13));
        }


        payPeriodService.getNextPayPeriod();


        timeClockService.clockIn(user.getUsername(), TimePunchCode.IN);
        timeClockService.clockOut(user.getUsername(), TimePunchCode.OUT);

        timeClockService.clockIn(user.getUsername(), TimePunchCode.LUNCH);






    ServiceMenuItem[] smis = {
            new ServiceMenuItem("BATTERY & ALTERNATOR CHECK",
                    "FREE!",
                    "We can load test your battery & alternator to make sure your vehicle's charging system is performing as required",
                    false,
                    "https://www.svgrepo.com/show/533724/engine-warning.svg", 1),
            new ServiceMenuItem("TIRE PRESSURE CHECK & ADJUST",
                    "FREE!",
                    "Make sure your vehicle has the correct air pressure in all of its tires",
                    false,
                    "https://www.svgrepo.com/show/444080/map-car-tire-pressure.svg", 1),
            new ServiceMenuItem("ENGINE CODE READING",
                    "FREE!",
                    "Pulls fault/error codes in vehicle's system, letting you know how urgent the light is",
                    false,
                    "https://www.svgrepo.com/show/533724/engine-warning.svg", 2),
        new ServiceMenuItem("OIL & FILTER CHANGE",
                "$35.00",
                "Regular oil maintenance is key to maintain proper lubrication for the engine's many internal components",
                false,
                "https://www.svgrepo.com/show/352304/oil-can.svg", 2),
        new ServiceMenuItem("ENGINE AIR FILTER REPLACEMENT",
                "$15.00",
                "A clean air filter will help your engine breathe easier which will increase your mpg!",
                false,
                "https://www.svgrepo.com/show/503160/air-filter.svg", 1),
        new ServiceMenuItem("CABIN AIR FILTER REPLACEMENT",
                "$75.00",
                "The filter for the a/c air inside the cabin",
                false,
                "https://www.svgrepo.com/show/500006/air-circulation.svg", 1),
        new ServiceMenuItem("TIRE ROTATION",
                "$40.00",
                "Keeps your tires wearing evenly by rotating their position on the vehicle",
                false,
                "https://www.svgrepo.com/show/145280/changing-car-tire.svg",  2),
        new ServiceMenuItem("TIRE REPAIR",
                "$40.00",
                "Repair a punctured tire",
                false,
                "https://www.svgrepo.com/show/322352/flat-tire.svg", 2),
        new ServiceMenuItem("COOLANT CHECK",
                "$40.00",
                "Check the level of coolant",
                false,
                "https://www.svgrepo.com/show/67466/engine-coolant.svg", 2),
        new ServiceMenuItem("COOLANT FLUSH",
                "$40.00",
                "Flush out the cooling system, replace coolant & bleed system",
                false,
                "https://www.svgrepo.com/show/500007/insufficient-coolant.svg", 4),
        new ServiceMenuItem("LIGHT BULB REPLACEMENT",
                "will vary",
                "Replace a faulty light bulb",
                false,
                "https://www.svgrepo.com/show/279048/light-bulb-invention.svg", 2),
        new ServiceMenuItem("REPLACE BRAKE PADS",
                "$150.00 - $200.00 (per axle)",
                "Clean, lube, & replace  brake parts",
                true,
                "https://www.svgrepo.com/show/10307/brake-disk.svg", 6),
        new ServiceMenuItem("BRAKE FLUID FLUSH",
                "$99.00",
                "Flush brake fluid system",
                true,
                "https://www.svgrepo.com/show/533550/brake-warning.svg", 4),
        new ServiceMenuItem("BATTERY REPLACEMENT",
                "$75.00",
                "Replace a dead battery",
                false,
                "https://www.svgrepo.com/show/313638/car-battery-solid.svg", 2),
        new ServiceMenuItem("GENERAL INSPECTION(FLUIDS, BELTS & HOSES)",
                "$50.00",
                "Visual inspection of the vehicle's suspension & engine components",
                false,
                "https://www.svgrepo.com/show/245352/car-repair-car.svg", 2),
        new ServiceMenuItem("SERPENTINE BELT REPLACEMENT",
                "$75.00",
                "Replace an aged or squeaky drive belt",
                false,
                "https://www.svgrepo.com/show/253125/engine-motor.svg", 2),
        new ServiceMenuItem("OTHER",
                "will vary",
                "",
                false,
                "https://www.svgrepo.com/show/315122/other-1.svg", 4)
    };

        Arrays.stream(smis).forEach(serviceMenuItemService::save);

//        for (int i = 0; i < 100; i++) {
        for (String v : Arrays.copyOf(vins, Math.min(SEEDED_VEHICLES, vins.length))) {
            int i = sampleIndex;
            String fname = FIRST_NAMES[i % FIRST_NAMES.length];
            String lname = LAST_NAMES[i % LAST_NAMES.length];
            String email = (fname + "." + lname + "@example.com").toLowerCase(Locale.ROOT);

            Customer newCustomer = new Customer(fname, lname, randomAddress(), randomContactInfo(), email);


            newCustomer = customerService.saveNewCustomer(newCustomer);

//            LocalDate sr = LocalDate.of(1965, Month.JANUARY, 1);
//            LocalDate se = LocalDate.of(2025, Month.JANUARY, 1);
//
//            LocalDate vehicleManDate = between(sr, se);


//            Vehicle newVehicle = new Vehicle();
//            newVehicle.setVin(faker.regexify(vinReg));
//            newVehicle.setModelYear(vehicleManDate.getYear());
//            newVehicle.setMake(faker.company().name());
//            newVehicle.setModel(faker.food().spice());


            Vehicle newVehicle = vehicleService.decodeVIN(v);
            newVehicle.setCustomer(newCustomer);
            newVehicle = vehicleService.saveNew(newVehicle);
        }


        System.out.println("---------------------**FINISHED!!!!**---------------------");

    }

    private Address randomAddress() {
        int i = sampleIndex++;

        Address a = new Address();
        a.setAddressLine1((100 + i * 17) + " " + STREETS[i % STREETS.length]);
        if (i % 3 == 0) a.setAddressLine2("Apt " + (i + 1));
        a.setCity(CITIES[i % CITIES.length]);
        a.setState("FL");
        Zipcode z = new Zipcode();
        z.setZip(ZIPS[i % ZIPS.length]);
        a.setZip(z);

        return a;
    }

    private ContactInformation randomContactInfo() {
        int i = sampleIndex++;

        ContactInformation c = new ContactInformation();
        if (i % 2 == 0) {
            c.setContactName(sampleName(i + 1));
            c.setAddress(randomAddress());
        }

        PhoneNumber p1 = new PhoneNumber();
        PhoneNumber p2 = new PhoneNumber();
        p1.setNumber(samplePhone(i));
        p2.setNumber(samplePhone(i + 100));
        c.setPrimaryPhone(p1);
        c.setAltPhone1(p2);
        if (i % 2 == 0) {
            PhoneNumber p3 = new PhoneNumber();
            p3.setNumber(samplePhone(i + 200));
            c.setAltPhone2(p3);
        }

        return c;
    }

    private String sampleName(int i) {
        return FIRST_NAMES[i % FIRST_NAMES.length] + " " + LAST_NAMES[i % LAST_NAMES.length];
    }

    private String samplePhone(int i) {
        return String.format("904555%04d", i % 10000);
    }

    private void newPayPeriod(LocalDate start, LocalDate end) {
        PayPeriod payPeriod = new PayPeriod();

        payPeriod.setPeriod(Period.ofWeeks(2));
        payPeriod.setStartDate(toDate(start));
        payPeriod.setEndDate(toDate(end));

        payPeriodService.save(payPeriod);
    }

    private static Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
