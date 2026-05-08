package Final;
import java.util.List;
import java.time.LocalDate;

/** Front desk: registration, billing, scheduling */
public class FrontDeskStaff extends Staff {
    public FrontDeskStaff(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    @Override public List<Appointment> viewAppointments(LocalDate date) {
        return scheduler.getAppointmentsForDate(date);
    }

    public Customer registerCustomer(String name, String addr, String id, String phone, String category) {
        CustomerCreator creator;
        switch (category.toLowerCase()) {
            case "fleet": creator = new FleetCustomerCreator(); break;
            case "staff": creator = new StaffCustomerCreator(); break;
            default:       creator = new PrivateCustomerCreator();
        }
        return creator.registerCustomer(name, addr, id, phone);
    }

    public void registerCar(String make, String model, String plate, Customer c) {
        CMRCenterApp.getInstance().addCar(new Car(make, model, plate, c));
    }

    public boolean scheduleAppointment(Appointment a) {
        return scheduler.scheduleAppointment(a);
    }

    public String printBill(Appointment a) {
        double base     = 50.0;
        double discount = base * a.getCustomer().getDiscountRate();
        double total    = base - discount;
        return String.format(
            "Bill ID: %s%nBase Service Fee: %.2f%nDiscount: %.2f%nTotal Amount Due: %.2f",
            java.util.UUID.randomUUID(),
            base,
            discount,
            total
        );
    }
}
// remove random