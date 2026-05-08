package Final;


public class StaffCustomer extends Person implements Customer {
    public StaffCustomer(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    @Override
    public double getDiscountRate() {
        return 0.50;
    }

    @Override
    public String getFullName() {
        return name;
    }
}