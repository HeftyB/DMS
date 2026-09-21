# DMS — Dealer Management System

[![CI](https://github.com/HeftyB/DMS/actions/workflows/ci.yml/badge.svg)](https://github.com/HeftyB/DMS/actions/workflows/ci.yml)

A Spring Boot application for running an independent auto repair shop: customers and vehicles,
repair orders, appointment scheduling, employee timekeeping, and the accounting records that hang
off them. Java 21, Spring Data JPA, Spring Security, Thymeleaf and SQL Server, with no front-end
framework.

I worked in auto repair before I wrote this, and built it around how a service drive actually runs.
Three things in the data model come from that: appointments consume an advisor's capacity in fixed
blocks rather than sitting at a timestamp, technician labor is tracked as flat-rate hours against a
job, and job-level time punches are kept separate from the employee time clock.

## How this was built

The commit history covers two separate periods of work.

**June 2024 – February 2025: the application, written by hand.** I designed and built DMS on my
own, before I used AI coding tools: 84 commits merged through 11 pull requests, ending at 185 Java
source files and about 11,500 lines of Java. The history shows decisions being revisited as the
code grew — dates moved from `ZonedDateTime` to `java.util.Date` in July 2024, then to
`java.time.LocalDate` that October, once the first choice had caused enough trouble. Work stopped
in February 2025 after a hardware failure cost me unpushed work, and the last commit that survived
sat on a branch until the pass below merged it.

**September 2026: a maintenance pass with Claude.** Using Claude Code, I added a test suite and CI,
merged that stranded commit, and fixed the defects below — some named in a code review of the
project, the rest found while writing the tests. Every non-merge commit from this pass carries a
`Co-Authored-By: Claude` trailer, apart from one Dependabot update, so the two periods can be told
apart in `git log`.

What it fixed:

- **Authentication.** The change-password check compared its arguments in the wrong order and could
  never succeed. Registration rejected any email address that contained an existing one. An unknown
  password-reset token threw instead of returning empty. Two "not found" errors produced a blank
  message or a formatting exception. The Passay password policy was written but never applied to
  anything.
- **Timekeeping.** Clocking out threw on a date conversion left over from the migration, and the
  midnight auto-clock-out job looked for *today's* shift at 00:00, so it could never have closed
  one.
- **Web layer.** `GET /search` returned 500 when called with no parameters, and the VIN search on
  the repair orders page posted to a route that only accepted GET. Two routes pointed at pages
  whose markup was among the work lost in the hardware failure; they now redirect to the screens
  that cover the same ground. Method security is switched on, so the role checks written on the
  admin controllers take effect.
- **Data.** A repair order's priority field carried a JavaScript-style regex that no value could
  match, including its own default — which would have rejected every new repair order the moment
  validation was enabled.
- **Housekeeping.** Debug code and placeholder controls, superseded commented-out code, a duplicate
  and two unused dependencies, and a 3.4 MB background image. The first-run seeder, commented out
  since October 2024, was restored and now runs under a profile.

The pass added 41 test cases (JUnit 5, MockMvc, in-memory H2) where there had been one empty stub,
and a GitHub Actions build. Spring Boot went from 3.3.1 to 3.5.16 with no source changes. Outside
the restored seeder, production Java changed by +90/−185 lines across 18 files and templates by
+3/−73; the rest of the application is as it was in February 2025.

## What it does

- **Customers and vehicles** — customers, vendors, employees and sales leads, with contact details
  and addresses. A VIN is decoded to year, make, model, trim and drivetrain through the
  [NHTSA vPIC API](https://vpic.nhtsa.dot.gov/api/).
- **Repair orders** — jobs written as concern, cause and correction, with parts, miscellaneous
  items, flat-rate labor per technician, and a status workflow. Technicians get their own
  work-order view.
- **Appointment scheduling** — an advisor's day is divided into fixed-length blocks, generated the
  first time that date is requested. Booking reserves the blocks an appointment needs; rescheduling
  or cancelling releases them.
- **Timekeeping** — clock in and out, job-level punches for flat-rate work, pay periods, timesheets
  and PTO, plus a scheduled job that closes any shift still open at midnight.
- **HR administration** — employees, user accounts, and roles grouped by department.
- **Security** — form login, BCrypt, CSRF protection, and role-based access to the admin area.
  Database credentials have always come from environment variables and have never been committed.
- **Accounting records** — invoices, statements with interest charges, purchase orders, payments,
  fees and taxes are modeled and stored, though no screens are built on them yet.

## Worth a look

- **[`AppointmentServiceImp`](src/main/java/com/heftyb/dms/appointments/AppointmentServiceImp.java)** —
  the block-based scheduler: generating a day's blocks on first request, reserving a contiguous run
  for an appointment, and freeing them again on reschedule or cancellation.
- **[The schedule grid](src/main/resources/templates/appointments.html)** — a CSS Grid whose row
  names Thymeleaf generates from the advisor's own time blocks (`th:inline="css"`), so the grid
  follows the shop's hours and block length with no layout maths in JavaScript. About 230 lines of
  plain JavaScript handle block selection, the booking dialog, and estimating an appointment's end
  time from the service items chosen; menu and advisor data cross into the browser through
  `th:inline="javascript"`.
- **[`fragments.html`](src/main/resources/templates/fragments.html)** — eight parameterized
  Thymeleaf fragments reused across the pages, including a customer form that renders read-only or
  editable from the same markup, and a VIN search dialog that posts wherever the calling page tells
  it to.
- **[`VehicleServiceImp`](src/main/java/com/heftyb/dms/vehicles/services/VehicleServiceImp.java)** —
  the VIN decoder integration, mapping the vPIC response into the vehicle record.
- **The print stylesheet** — a repair order prints with the navigation and controls hidden and a
  customer signature line shown in their place, through `print-hide` and `print-only` classes and
  an `@media print` block. Shops print repair orders.
- **[`WebSecurityConfig`](src/main/java/com/heftyb/dms/config/WebSecurityConfig.java)** and
  **[`Auditable`](src/main/java/com/heftyb/dms/dao/Auditable.java)** — the security rules, and the
  JPA auditing base class that stamps who created or changed a record.

The front end is deliberately plain: two hand-written stylesheets of about a thousand lines, no
framework, no build step, and one external library (Chart.js) on the HR dashboard.

## Known gaps

It is a working prototype, not a finished product.

- **The layout targets desktop.** The public landing page has two breakpoints, but the pages behind
  the login sit at a fixed width and neither adapt to a narrow window nor fill a wide one.
- **The HR dashboard is a mockup.** Its charts draw sample values, not real figures.
- **Accounting is modeled, not wired up.** Entities and services exist for invoices, purchase
  orders and taxes, but there are no screens, and the finance-charge calculation is a TODO.
- **The sales side was never started**, despite the name — vehicle inventory, floor plan and deals.
- **Authorization is coarse.** Only the admin area requires a role; every other page is open to any
  signed-in user.
- **Some queries read a whole table** and filter in Java, most noticeably in scheduling. That is
  fine for one shop and wrong at any larger scale.
- **No password-reset or change-password screens.** The service layer for them exists and is
  tested, but nothing calls it. There is no sign-up page by design: HR creates accounts.
- **Two known defects remain.** Searching by first *and* last name with vehicle results throws
  `ConcurrentModificationException`; the upload directory is never created, so the first file
  upload fails, and `load()` lacks the path check `store()` has.
- **File uploads are adapted from Spring's
  [Uploading Files](https://spring.io/guides/gs/uploading-files) guide**, not original work.

## Running it

The tests need only a JDK 21. They run against in-memory H2, and the Maven wrapper is included:

```bash
git clone https://github.com/HeftyB/DMS.git
cd DMS
./mvnw verify
```

Running the application also needs a reachable SQL Server instance:

```bash
export DATABASE_CONNECTION_URL="jdbc:sqlserver://localhost:1433;databaseName=dms;encrypt=false"
export DATABASE_CONNECTION_USERNAME="your_user"
export DATABASE_CONNECTION_PASSWORD="your_password"
./mvnw spring-boot:run
```

It listens on http://localhost:8008 (override with `PORT`), and Hibernate creates the schema on
first start. A T-SQL export of the schema is in
[`database_init.sql`](src/main/java/com/heftyb/dms/database_init.sql) for reference.

### First run

A new database has no users, and there is no sign-up page: accounts are created by HR inside the
application, so the first administrator comes from the seed profile. Run it once against an empty
database:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=seed
```

It creates roles, users, employees, the service menu, six pay periods around today, and a dozen
customers whose vehicles are decoded through the NHTSA API, so it needs network access and takes a
few minutes. Sign in as **hefty / hefty-demo-pass**, an administrator. Start the application
normally afterwards and the seeder stays out of the way.

## Stack

| | |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5: Web, Data JPA, Security, Validation, Thymeleaf |
| Database | Microsoft SQL Server; in-memory H2 for tests |
| Front end | Server-rendered Thymeleaf and plain JavaScript; Chart.js on the HR dashboard |
| Validation | Jakarta Bean Validation, Passay for the password policy |
| External API | NHTSA vPIC, for VIN decoding |
| Build and CI | Maven wrapper, GitHub Actions, Dependabot |
| Tests | JUnit 5, Spring MockMvc, Spring Security Test, AssertJ |

## Layout

Packages are organized by feature rather than by layer:

```
src/main/java/com/heftyb/dms/
├── account/       invoices, statements, purchase orders, payments, fees, taxes, pay periods
├── appointments/  scheduling, advisor time blocks, service menu
├── crm/           customers, employees, vendors, sales leads
├── inventory/     parts
├── repairorder/   repair orders, jobs, flat-rate labor
├── timekeeping/   time punches, timesheets, PTO
├── users/         user accounts and roles
├── vehicles/      vehicles and VIN decoding
├── config/        security and file storage configuration
├── controllers/   admin, search and landing pages
├── dao/           auditing base class
├── exceptions/    domain exceptions
├── fileuploads/   file storage
└── validation/    password policy, verification and reset tokens
```

## License

MIT. See [LICENSE](LICENSE).
