package Final;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentScheduler {
    private List<Appointment> appointments;

    public AppointmentScheduler(List<Appointment> apps) {
        this.appointments = apps;
    }

    public boolean scheduleAppointment(Appointment a) {
        LocalDate today = LocalDate.now();
        if (!a.getTime().toLocalDate().equals(today)
         || a.getTime().getMinute() != 0
         || a.getTime().getHour() < 9
         || a.getTime().getHour() > 17) {
            return false;
        }
        for (Appointment x : appointments) {
            if (x.getTime().equals(a.getTime())) return false;
        }
        appointments.add(a);
        return true;
    }

    public List<Appointment> getAppointmentsForDate(LocalDate date) {
        List<Appointment> out = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getTime().toLocalDate().equals(date)) out.add(a);
        }
        return out;
    }
}