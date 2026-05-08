package Final;


public interface PaymentStrategy {
    /** Process already‐discounted amount; return final paid */
    double process(double amount);
}