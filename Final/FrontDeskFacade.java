// File: FrontDeskFacade.java
package Final;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Front-Desk Facade: hides all the nitty-gritty behind four simple methods.
 */
public class FrontDeskFacade {
   
    private final PaymentContext  paymentContext = new PaymentContext();
    
    private final FrontDeskStaff front;
    public FrontDeskFacade(FrontDeskStaff front) {
        this.front = front;
    }

    // 1) Register a new customer + car
    public Customer registerCustomerAndCar(
            String category,
            String name,
            String address,
            String nationalID,
            String phone,
            String carMake,
            String carModel,
            String plateNumber
    ) {
        // register customer
        Customer c = front.registerCustomer(name, address, nationalID, phone, category);
        // register car
        front.registerCar(carMake, carModel, plateNumber, c);
        return c;
    }

    // 2) Schedule an appointment for an existing customer + plate
    public boolean scheduleAppointment(
            String nationalID,
            String plateNumber,
            LocalDateTime when,
            int paymentMethodChoice
    ) {
        // lookup customer
        Customer c = CMRCenterApp.getInstance().listAllCustomers().stream()
                         .filter(x -> x.getNationalID().equals(nationalID))
                         .findFirst().orElse(null);
        if (c == null) return false;

        // lookup car
        Car car = CMRCenterApp.getInstance().listAllCars().stream()
                    .filter(x -> x.getPlate().equals(plateNumber)
                              && x.getOwner().getNationalID().equals(nationalID))
                    .findFirst().orElse(null);
        if (car == null) return false;

        // build appointment
        Appointment appt = new Appointment(when, c, car);

        // pick & store payment strategy
        PaymentStrategy strat;
        String methodName;
        switch (paymentMethodChoice) {
          case 2:
            strat     = new MobileWalletPaymentStrategy();
            methodName = "Mobile App";
            break;
          case 3:
            strat      = new CliqPaymentStrategy();
            methodName = "Cliq";
            break;
          default:
            strat      = new CashPaymentStrategy();
            methodName = "Cash";
        }
        paymentContext.setStrategy(strat);
        appt.setPaymentStrategy(strat);
        appt.setPaymentMethodName(methodName);

        // schedule
        return front.scheduleAppointment(appt);
    }

    // helper: fetch all cars for a given customer ID
    public List<Car> getCarsForCustomer(String nationalID) {
        return CMRCenterApp.getInstance().listAllCars().stream()
            .filter(x -> x.getOwner().getNationalID().equals(nationalID))
            .collect(Collectors.toList());
    }

    // 3) Print a bill for a specific appointment
    public String generateBill(String customerID, LocalDateTime when) {
        Appointment appt = CMRCenterApp.getInstance().listAllAppointments().stream()
            .filter(a -> a.getCustomer().getNationalID().equals(customerID)
                      && a.getTime().equals(when))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        double baseFee   = 50.0;
     // only discount on the 50JD service charge:
     double discount  = baseFee * appt.getCustomer().getDiscountRate();
     double netAmount = baseFee - discount;

     // still run through your (no-op) PaymentContext
     double charged = paymentContext.pay(netAmount);
     return String.join("\n",
    		    "Bill for: "        + appt.getCustomer().getFullName(),
    		    "Appointment: "     + appt.getTime(),
    		    "Base Service Fee: "+ baseFee,
    		    "Discount: "        + discount,
    		    "Total Amount Due: "+ charged
    		);
    }

    // 4) List all appointments for today
    public List<Appointment> listTodaysAppointments() {
        return front.viewAppointments(LocalDate.now());
    }
}
