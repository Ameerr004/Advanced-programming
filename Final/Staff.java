package Final;



import java.time.LocalDate;
import java.util.List;

/** Abstract staff member */
public abstract class Staff extends Person {
    protected AppointmentScheduler scheduler = CMRCenterApp.getInstance().getScheduler();

    public Staff(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    public abstract List<Appointment> viewAppointments(LocalDate date);
}
