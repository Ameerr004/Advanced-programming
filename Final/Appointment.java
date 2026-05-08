package Final;


import java.time.LocalDateTime;

/** Service appointment */
public class Appointment {
    private LocalDateTime time;
    private Customer customer;
    private Car car;
    private ServiceReport serviceReport;
    private DiagnosticReport diagnosticReport;
    private PaymentStrategy paymentStrategy;
    private String paymentMethodName;

    public Appointment(LocalDateTime time, Customer customer, Car car) {
        this.time = time;
        this.customer = customer;
        this.car = car;
    }

    public LocalDateTime getTime() { return time; }
    public Customer getCustomer() { return customer; }
    public Car getCar() { return car; }
    public ServiceReport getServiceReport() { return serviceReport; }
    public void setServiceReport(ServiceReport s) { this.serviceReport = s; }
    public DiagnosticReport getDiagnosticReport() { return diagnosticReport; }
    public void setDiagnosticReport(DiagnosticReport d) { this.diagnosticReport = d; }
    public LocalDateTime getAppointmentTime() {
        return this.time;
    }
    
    public void setPaymentStrategy(PaymentStrategy strat) {
        this.paymentStrategy = strat;
    }
    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }
    public void setPaymentMethodName(String name) {
        this.paymentMethodName = name;
    }
    public String getPaymentMethodName() {
        return paymentMethodName;
    }
}

