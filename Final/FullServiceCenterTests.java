
package Final;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FullServiceCenterTests {

    private FrontDeskFacade facade;
    private FrontDeskStaff front;
    private CMRCenterApp app;

    @BeforeEach
    void init() {
        app = CMRCenterApp.getInstance();
        app.listAllAppointments().clear();
        app.listAllCars().clear();
        app.listAllCustomers().clear();
        app.listAllStaff().clear();

        front = new FrontDeskStaff("Front", "Addr", "FD1", "0000");
        app.addStaff(front);
        facade = new FrontDeskFacade(front);
    }

    // BILLING TESTS

    @Test
    void BILL_01_BillForPrivateCustomer() {
        Customer c = facade.registerCustomerAndCar("private", "Alice", "Addr", "ID123", "0777", "Toyota", "Camry", "ABC-1");
        LocalDateTime time = LocalDate.now().atTime(14, 0);
        assertTrue(facade.scheduleAppointment("ID123", "ABC-1", time, 1));
        String result = facade.generateBill("ID123", time);
        assertTrue(result.contains("Base Service Fee: 50.0"));
        assertTrue(result.contains("Discount: 0.0"));
        assertTrue(result.contains("Total Amount Due: 50.0"));
    }

    @Test
    void BILL_02_BillForStaffCustomer() {
        Customer c = facade.registerCustomerAndCar("staff", "John", "Addr", "STAFF01", "1234", "BMW", "X5", "ST-1");
        LocalDateTime time = LocalDate.now().atTime(15, 0);
        assertTrue(facade.scheduleAppointment("STAFF01", "ST-1", time, 1));
        String bill = facade.generateBill("STAFF01", time);
        assertTrue(bill.contains("Discount: 25.0"));
        assertTrue(bill.contains("Total Amount Due: 25.0"));
    }

    @Test
    void BILL_03_AppointmentNotFoundThrows() {
        LocalDateTime time = LocalDateTime.of(2025, 6, 10, 16, 0);
        assertThrows(IllegalArgumentException.class, () -> facade.generateBill("XXX", time));
    }

    // REGISTRATION TESTS

    @Test
    void REG_01_RegisterPrivateCustomerAndCar() {
        Customer c = facade.registerCustomerAndCar("private", "Alice", "Addr", "ID123", "0777", "Toyota", "Camry", "ABC-1");
        assertTrue(c instanceof PrivateCustomer);
        assertEquals("Alice", c.getFullName());
        assertEquals("ABC-1", app.listAllCars().get(0).getPlate());
    }

    @Test
    void REG_02_RegisterFleetCustomerAndCar() {
        Customer c = facade.registerCustomerAndCar("fleet", "FleetCo", "Addr2", "F123", "0799", "Ford", "Focus", "XYZ-9");
        assertTrue(c instanceof FleetCustomer);
        assertEquals(0.30, c.getDiscountRate(), 0.0001);
    }

    @Test
    void REG_03_InvalidCategoryDefaultsToPrivate() {
        Customer c = facade.registerCustomerAndCar("VIP", "Bob", "Addr3", "ID456", "0788", "Honda", "Civic", "LMN-5");
        assertTrue(c instanceof PrivateCustomer);
        assertEquals(0.0, c.getDiscountRate(), 0.0001);
    }

    // APPOINTMENT TESTS

    @Test
    void APPT_01_ScheduleValidEmptySlot() {
        facade.registerCustomerAndCar("private", "C1", "Addr", "C1", "000", "Toyota", "Yaris", "P1");
        
        // Use today's date at 10:00 to avoid mismatch with LocalDate.now()
        LocalDateTime time = LocalDate.now().atTime(10, 0);
        
        assertTrue(facade.scheduleAppointment("C1", "P1", time, 1));
    }


    @Test
    void APPT_02_RejectOverlappingAppointment() {
        facade.registerCustomerAndCar("private", "C1", "Addr", "C1", "000", "Toyota", "Yaris", "P1");
        LocalDateTime time = LocalDate.now().atTime(11, 0);
        assertTrue(facade.scheduleAppointment("C1", "P1", time, 1));
        assertFalse(facade.scheduleAppointment("C1", "P1", time, 2)); // Overlapping
    }

    @Test
    void APPT_03_RejectOffHoursOrWrongDateAppointment() {
        facade.registerCustomerAndCar("private", "C1", "Addr", "C1", "000", "Toyota", "Yaris", "P1");
        LocalDateTime halfHour = LocalDateTime.of(2025, 6, 10, 11, 30);
        LocalDateTime futureDate = LocalDateTime.of(2025, 6, 11, 18, 0);
        assertFalse(facade.scheduleAppointment("C1", "P1", halfHour, 3));
        assertFalse(facade.scheduleAppointment("C1", "P1", futureDate, 1));
    }
}
