// CMRCenterApp.java
package Final;

import java.util.ArrayList;
import java.util.List;

/** Central application (aggregates domain) */
public class CMRCenterApp {
    private static final CMRCenterApp INSTANCE = new CMRCenterApp();

    private List<Customer> customers = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<Staff> staffMembers = new ArrayList<>();
    private AppointmentScheduler scheduler = new AppointmentScheduler(appointments);

    private CMRCenterApp() {}
    public static CMRCenterApp getInstance() { return INSTANCE; }

    // Aggregations:
    public void addCustomer(Customer c)     { customers.add(c); }
    public void addCar(Car c)               { cars.add(c); }
    public void addStaff(Staff s)           { staffMembers.add(s); }
    public boolean scheduleAppointment(Appointment a) { return scheduler.scheduleAppointment(a); }

    public List<Customer> listAllCustomers()      { return customers; }
    public List<Car> listAllCars()                { return cars; }
    public List<Appointment> listAllAppointments(){ return appointments; }
    public List<Staff> listAllStaff()             { return staffMembers; }
    public AppointmentScheduler getScheduler()    { return scheduler; }
}
