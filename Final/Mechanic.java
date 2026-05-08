package Final;


import java.time.LocalDate;
import java.util.List;

/** Mechanic: performs and records repairs */
public class Mechanic extends Staff {
    public Mechanic(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    @Override public List<Appointment> viewAppointments(LocalDate date) {
        return scheduler.getAppointmentsForDate(date);
    }

    public void addServiceReport(Appointment a, List<String> repairs) {
        a.setServiceReport(new ServiceReport(repairs));
    }
}