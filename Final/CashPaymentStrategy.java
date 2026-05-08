package Final;



public class CashPaymentStrategy implements PaymentStrategy {
    @Override public double process(double amount) { return amount; }
}
