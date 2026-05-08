package Final;


public class PrivateCustomerCreator extends CustomerCreator {
    public PrivateCustomerCreator() {
        super("FrontDesk","Center","000","000");
    }

    @Override
    public Customer createCustomer(String n, String a, String id, String p) {
        return new PrivateCustomer(n, a, id, p);
    }
}
