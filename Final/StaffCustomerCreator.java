package Final;

public class StaffCustomerCreator extends CustomerCreator {
    public StaffCustomerCreator() {
        super("FrontDesk","Center","000","000");
    }

    @Override
    public Customer createCustomer(String n, String a, String id, String p) {
        return new StaffCustomer(n, a, id, p);
    }
}