package Final;


public class FleetCustomerCreator extends CustomerCreator {
    public FleetCustomerCreator() {
        super("FrontDesk","Center","000","000");
    }

    @Override
    public Customer createCustomer(String n, String a, String id, String p) {
        return new FleetCustomer(n, a, id, p);
    }
}
