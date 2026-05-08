package Final;


public class PaymentContext {
// high-level module depends on the abstraction
    private PaymentStrategy strategy;
    public void setStrategy(PaymentStrategy s) { this.strategy = s; }
    
    public double pay(double amount) {
    	 // calls only the interface method, not a concrete class
    	return strategy.process(amount); 
    	}
}
