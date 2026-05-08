package Final;

public class FleetCustomer extends Person implements Customer {
    public FleetCustomer(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    @Override
    public double getDiscountRate() {
        return 0.30;
    }

    @Override
    public String getFullName() {
        return name;
    }
}