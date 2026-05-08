package Final;


public class PrivateCustomer extends Person implements Customer {
    public PrivateCustomer(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    @Override
    public double getDiscountRate() {
        return 0.0;
    }

    @Override
    public String getFullName() {
        return name;
    }
}

