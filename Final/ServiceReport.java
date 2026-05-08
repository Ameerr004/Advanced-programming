package Final;

import java.util.List;

/** Mechanic’s service report */
public class ServiceReport implements Report {
    private List<String> repairs;
  

    public ServiceReport(List<String> repairs) {
        this.repairs = repairs;
      
    }

 
    @Override
    public String generateReport() {
        return "Repairs: " + repairs + ", Parts cost: ";
    }
}