// File: Main.java
package Final;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry 1point for the CMR Center Management System.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CMRCenterApp app = CMRCenterApp.getInstance();

        // Initialize staff members
        FrontDeskStaff front = new FrontDeskStaff("Alice", "Center HQ", "FD01", "0790000001");
      
        Mechanic mech = new Mechanic("Bob", "Center HQ", "ME01", "0790000002");
        Technician tech = new Technician("Carol", "Center HQ", "TE01", "0790000003");
        FrontDeskFacade facade = new FrontDeskFacade(front);

        app.addStaff(front);
        app.addStaff(mech);
        app.addStaff(tech);
       
        DateTimeFormatter dtfDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dtfDate     = DateTimeFormatter.ISO_LOCAL_DATE;

        // Main role-selection loop
        while (true) {
            System.out.println("=== Welcome to CMR Management System ===\n");
            System.out.println("Choose your role (or 0 to exit):");
            System.out.println(" 1) Front Desk Staff");
            System.out.println(" 2) Mechanic Staff");
            System.out.println(" 3) Technician Staff");
            System.out.println(" 0) Exit");
            System.out.print("Enter choice: ");
            String role = sc.nextLine().trim();

            switch (role) {
                case "1":
                	handleFrontDesk(sc, facade, dtfDateTime);
                    break;
                case "2":
                    handleMechanic(mech, sc, dtfDateTime, dtfDate);
                    break;
                case "3":
                    handleTechnician(tech, sc, dtfDateTime, dtfDate);
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

 // --- Front Desk UI via Facade ---
 // --- Front-Desk Menu (via Facade) ---

    private static void handleFrontDesk(Scanner sc,
                                        FrontDeskFacade facade,
                                        DateTimeFormatter dtf) {
        while (true) {
            System.out.println("\n--- Front Desk Staff ---");
            System.out.println(" 1) Register Customer + Car");
            System.out.println(" 2) Schedule Appointment");
            System.out.println(" 3) Print Bill");
            System.out.println(" 4) List Today's Appointments");
            System.out.println(" 0) Back to Role Selection");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // 1) Register Customer + Car
                    System.out.print(" Full Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print(" Address: ");
                    String addr = sc.nextLine().trim();
                    System.out.print(" National ID: ");
                    String nid  = sc.nextLine().trim();
                    System.out.print(" Contact Number: ");
                    String phone= sc.nextLine().trim();
                    System.out.print(" Category (private/fleet/staff): ");
                    String cat  = sc.nextLine().trim().toLowerCase();

                    System.out.print(" Car Make: ");
                    String make = sc.nextLine().trim();
                    System.out.print(" Car Model: ");
                    String model= sc.nextLine().trim();
                    System.out.print(" Plate Number: ");
                    String plate= sc.nextLine().trim();

                    // single facade call:
                    Customer c = facade.registerCustomerAndCar(
                        cat, name, addr, nid, phone,
                        make, model, plate
                    );
                    System.out.println("✅ Customer and car registered: " 
                                       + c.getFullName());
                    break;

                case "2":
                    // 2) Schedule Appointment
                    System.out.print(" Customer National ID: ");
                    String sid = sc.nextLine().trim();

                    // list cars for this cust:
                    List<Car> cars = facade.getCarsForCustomer(sid);
                    if (cars.isEmpty()) {
                        System.out.println("No cars found for " + sid);
                        break;
                    }
                    System.out.println("  Cars:");
                    cars.forEach(car -> 
                        System.out.println("   • " + car)
                    );

                    System.out.print(" Plate Number: ");
                    String schPlate = sc.nextLine().trim();

                    System.out.print(" Appointment (yyyy-MM-dd HH:mm): ");
                    LocalDateTime when;
                    try {
                        when = LocalDateTime.parse(sc.nextLine().trim(), dtf);
                    } catch (Exception ex) {
                        System.out.println("❌ Invalid date/time.");
                        break;
                    }

                    System.out.print(" Payment Method (1=Cash,2=Mobile App,3=Cliq): ");
                    int pm;
                    try {
                        pm = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException ex) {
                        System.out.println("❌ Invalid payment choice.");
                        break;
                    }

                    boolean ok = facade.scheduleAppointment(sid, schPlate, when, pm);
                    System.out.println(ok
                        ? "✅ Appointment scheduled at " + when
                        : "❌ Failed to schedule. Check constraints.");
                    break;

                case "3":
                    // 3) Print Bill
                    System.out.print(" Customer National ID: ");
                    String bid = sc.nextLine().trim();
                    System.out.print(" Appointment (yyyy-MM-dd HH:mm): ");
                    LocalDateTime dt;
                    try {
                        dt = LocalDateTime.parse(sc.nextLine().trim(), dtf);
                    } catch (Exception ex) {
                        System.out.println("❌ Bad date/time format.");
                        break;
                    }

                    try {
                        String bill = facade.generateBill(bid, dt);
                        System.out.println("\n" + bill);
                    } catch (IllegalArgumentException iae) {
                        System.out.println("❌ " + iae.getMessage());
                    }
                    break;

                case "4":
                    // 4) List Today's Appointments
                    List<Appointment> today = facade.listTodaysAppointments();
                    if (today.isEmpty()) {
                        System.out.println("No appointments for today.");
                    } else {
                        System.out.println("\nToday's Appointments:");
                        today.forEach(a -> System.out.printf(
                            " - %s | %s | %s%n",
                            a.getTime().format(dtf),
                            a.getCustomer().getNationalID(),
                            a.getCar()
                        ));
                    }
                    break;

                case "0":
                    // back to role selection
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }




    // --- Mechanic UI ---
    private static void handleMechanic(Mechanic mech,
                                       Scanner sc,
                                       DateTimeFormatter dtfDT,
                                       DateTimeFormatter dtfD) {
        while (true) {
            System.out.println("\n--- Mechanic Staff ---");
            System.out.println(" 1) View Appointments for a Date");
            System.out.println(" 2) Add Service Report");
            System.out.println(" 0) Back to Role Selection");
            System.out.print("Enter choice: ");
            String c = sc.nextLine().trim();

            switch (c) {
                case "1":
                    System.out.print(" Date (yyyy-MM-dd): ");
                    LocalDate date;
                    try { date = LocalDate.parse(sc.nextLine().trim(), dtfD);
                    } catch (Exception ex) {
                        System.out.println("Invalid date."); break;
                    }
                    List<Appointment> list = mech.viewAppointments(date);
                    if (list.isEmpty()) {
                        System.out.println("No appointments found for " + date);
                    } else {
                        list.forEach(a -> System.out.printf(" - %s | ID: %s | Car: %s\n",
                            a.getTime().format(dtfDT),
                            a.getCustomer().getNationalID(),
                            a.getCar()));
                    }
                    break;

                case "2":
                    System.out.print(" Customer National ID: ");
                    String mid = sc.nextLine().trim();
                    System.out.print(" Appointment Date (yyyy-MM-dd): ");
                    LocalDate mDate;
                    try {
                        mDate = LocalDate.parse(sc.nextLine().trim(), dtfD);
                    } catch (Exception ex) {
                        System.out.println("Invalid date.");
                        break;
                    }

                    Appointment mAppt = findAppointmentByDate(mid, mDate);
                    if (mAppt == null) {
                        System.out.println("Appointment not found.");
                        break;
                    }

                    // Ask only for repair descriptions (no parts cost)
                    System.out.print(" How many repairs? ");
                    int repairCount = Integer.parseInt(sc.nextLine().trim());
                    List<String> repairs = new ArrayList<>();
                    for (int i = 1; i <= repairCount; i++) {
                        System.out.print("  Repair " + i + ": ");
                        repairs.add(sc.nextLine().trim());
                    }
                    
                    mech.addServiceReport(mAppt, repairs);
                    System.out.println("Service report added.");
                    break;

                case "0": return;
                default:   System.out.println("Invalid choice.");
            }
        }
    }

    // --- Technician UI ---
    private static void handleTechnician(Technician tech,
                                         Scanner sc,
                                         DateTimeFormatter dtfDT,
                                         DateTimeFormatter dtfD) {
        while (true) {
            System.out.println("\n--- Technician Staff ---");
            System.out.println(" 1) View Appointments for a Date");
            System.out.println(" 2) Add Diagnostic Report");
            System.out.println(" 3) Generate Daily Report");
            System.out.println(" 0) Back to Role Selection");
            System.out.print("Enter choice: ");
            String c = sc.nextLine().trim();

            switch (c) {
                case "1":
                    System.out.print(" Date (yyyy-MM-dd): ");
                    LocalDate date;
                    try { date = LocalDate.parse(sc.nextLine().trim(), dtfD);
                    } catch (Exception ex) {
                        System.out.println("Invalid date."); break;
                    }
                    List<Appointment> list = tech.viewAppointments(date);
                    if (list.isEmpty()) {
                        System.out.println("No appointments found for " + date);
                    } else {
                        list.forEach(a -> System.out.printf(" - %s | ID: %s | Car: %s\n",
                            a.getTime().format(dtfDT),
                            a.getCustomer().getNationalID(),
                            a.getCar()));
                    }
                    break;

                case "2":
                	System.out.print(" Customer National ID: ");
                    String tid = sc.nextLine().trim();
                    System.out.print(" Appointment Date (yyyy-MM-dd): ");
                    LocalDate tDate;
                    try {
                        tDate = LocalDate.parse(sc.nextLine().trim(), dtfD);
                    } catch (Exception ex) {
                        System.out.println("Invalid date.");
                        break;
                    }

                    Appointment tAppt = findAppointmentByDate(tid, tDate);
                    if (tAppt == null) {
                        System.out.println("Appointment not found.");
                        break;
                    }

                    // Ask only for issues & actions (no fee prompt)
                    System.out.print(" How many issues found? ");
                    int ic = Integer.parseInt(sc.nextLine().trim());
                    List<String> issues = new ArrayList<>();
                    for (int i = 1; i <= ic; i++) {
                        System.out.print("  Issue " + i + ": ");
                        issues.add(sc.nextLine().trim());
                    }

                    System.out.print(" How many recommended actions? ");
                    int ac = Integer.parseInt(sc.nextLine().trim());
                    List<String> actions = new ArrayList<>();
                    for (int i = 1; i <= ac; i++) {
                        System.out.print("  Action " + i + ": ");
                        actions.add(sc.nextLine().trim());
                    }

                    // Pass zero for diagnostic cost
                    tech.addDiagnosticReport(tAppt, issues, actions);
                    System.out.println("Diagnostic report added.");
                    break;
                    
                case "3":
                    System.out.print(" Date (yyyy-MM-dd): ");
                    LocalDate rd;
                    try {
                        rd = LocalDate.parse(sc.nextLine().trim(), dtfD);
                    } catch (Exception ex) {
                        System.out.println("❌ Invalid date format.");
                        break;
                    }

                    // fetch all appointments for that date
                    List<Appointment> all = tech.viewAppointments(rd);

                    // **Generate the file-based report**
                    tech.generateDailyReport(all);
                    // tech.generateDailyReport(...) already prints the “✅ daily_report.txt saved” or error.

                    break;

                case "0": return;
                default:    System.out.println("Invalid choice.");
            }
        }
    }

    // --- Helper methods ---
    private static Customer findCustomerById(String id) {
        return CMRCenterApp.getInstance().listAllCustomers().stream()
            .filter(c -> c.getFullName() != null && c.getFullName().length()>0 && ((Person)c).nationalID.equals(id))
            .findFirst().orElse(null);
    }

    private static Car findCarByPlate(String plate) {
        return CMRCenterApp.getInstance().listAllCars().stream()
            .filter(c -> c.getPlate().equals(plate))
            .findFirst().orElse(null);
    }

    private static Appointment findAppointment(String id, LocalDateTime when) {
        return CMRCenterApp.getInstance().listAllAppointments().stream()
            .filter(a -> a.getCustomer().getNationalID().equals(id)
                       && a.getTime().equals(when))
            .findFirst().orElse(null);
    }

    private static Appointment findAppointmentByDate(String id, LocalDate date) {
        return CMRCenterApp.getInstance().listAllAppointments().stream()
            .filter(a -> a.getCustomer().getNationalID().equals(id)
                       && a.getTime().toLocalDate().equals(date))
            .findFirst().orElse(null);
    }
}
