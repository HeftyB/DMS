package com.heftyb.dms;

import com.heftyb.dms.crm.*;
import com.heftyb.dms.crm.services.ContactService;
import com.heftyb.dms.crm.services.CustomerService;
import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.repairorder.RepairOrder;
import com.heftyb.dms.repairorder.RepairOrderJob;
import com.heftyb.dms.repairorder.services.RepairOrderJobService;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import com.heftyb.dms.vehicles.Manufacturer;
import com.heftyb.dms.vehicles.Model;
import com.heftyb.dms.vehicles.Vehicle;
import com.heftyb.dms.vehicles.services.ManufacturerService;
import com.heftyb.dms.vehicles.services.ModelService;
import com.heftyb.dms.vehicles.services.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

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

    public SeedData(final ManufacturerService manufacturerService,
                    final VehicleService vehicleService,
                    final ModelService modelService,
                    final ContactService contactService,
                    final CustomerService customerService,
                    final RepairOrderService repairOrderService,
                    final RepairOrderJobService jobService,
                    final EmployeeService employeeService) {
        this.manufacturerService = manufacturerService;
        this.vehicleService = vehicleService;
        this.modelService = modelService;
        this.contactService = contactService;
        this.customerService = customerService;
        this.repairOrderService = repairOrderService;
        this.employeeService = employeeService;
        this.jobService = jobService;
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


        Employee e = new Employee("Hefty", "Burrito", "Hefty", mailingAddress, "5555555", JobTitle.GENERAL_MANAGER);

        e = employeeService.save(e);

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

        for(RepairOrderJob j : jobs) {
            r.getJobs().add(j);
        }

//        r = repairOrderService.save(r);

        System.out.println("---------------------**FINISHED!!!!**---------------------");

//        manufacturers.stream().forEach((m)-> {System.out.println(m);});
//        repairOrderService.findAll().stream().forEachOrdered((rr)-> {System.out.println(rr);});
//        System.out.println(manufacturer);
//        customerService.findAll().iterator().forEachRemaining(System.out::println);
//        employeeService.findAll().iterator().forEachRemaining(System.out::println);
//        vehicleService.findAll().iterator().forEachRemaining(System.out::println);
        /// code to be run here!
    }
}
