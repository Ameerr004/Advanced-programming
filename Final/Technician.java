package Final;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/** Technician: assists and documents diagnostics */
public class Technician extends Staff {
    public Technician(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    @Override public List<Appointment> viewAppointments(LocalDate date) {
        return scheduler.getAppointmentsForDate(date);
    }

    public void addDiagnosticReport(Appointment a, List<String> issues, List<String> actions) {
        a.setDiagnosticReport(new DiagnosticReport(issues, actions));
    }
    public void generateDailyReport(List<Appointment> appointments) {
        String fileName = "daily_report.txt";
        boolean wroteAny = false;

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Appointment appt : appointments) {
                if (appt.getServiceReport()  != null
                 && appt.getDiagnosticReport() != null) {

                    wroteAny = true;

                    writer.write("Customer National ID: "
                               + appt.getCustomer().getNationalID() + "\n");
                    writer.write("Car Plate Number:     "
                               + appt.getCar().getPlate() + "\n");
                    writer.write("Appointment Time:     "
                               + appt.getTime() + "\n");
                    writer.write("Service Report:\n"
                               + appt.getServiceReport().generateReport() + "\n");
                    writer.write("Diagnostic Report:\n"
                               + appt.getDiagnosticReport().generateReport() + "\n");
                    writer.write("------------------------------------------------\n");
                }
            }

            if (!wroteAny) {
                writer.write("No entries found for today’s report.\n");
            }

            System.out.println("✅ Daily report saved to: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error writing report: " + e.getMessage());
        }
    }
}
