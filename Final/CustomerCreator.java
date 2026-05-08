package Final;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomerCreator extends Person {
    protected CMRCenterApp app = CMRCenterApp.getInstance();

    public CustomerCreator(String name, String address, String nationalID, String phoneNumber) {
        super(name, address, nationalID, phoneNumber);
    }

    public abstract Customer createCustomer(String name, String address, String nationalID, String phoneNumber);

    /** Factory + register */
    public Customer registerCustomer(String name, String address, String nationalID, String phoneNumber) {
        Customer c = createCustomer(name, address, nationalID, phoneNumber);
        app.addCustomer(c);
        return c;
    }
}
