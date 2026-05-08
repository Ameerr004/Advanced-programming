package Final;


import java.util.List;

/** Technician’s diagnostic report */
public class DiagnosticReport implements Report {
    private List<String> issues;
    private List<String> recommendedActions;
    

    public DiagnosticReport(List<String> issues, List<String> recommendedActions) {
        this.issues = issues;
        this.recommendedActions = recommendedActions;
    
    }

   

    @Override
    public String generateReport() {
        return "Issues: " + issues
             + ", Actions: " + recommendedActions;
             
    }
}