# Project Report: Car Maintenance and Repair Center Management System (CMR System)

## 1. Introduction
This report designs a **Car Maintenance and Repair Center Management System (CMR System)** that manages the operations of a vehicle service center. The system handles customer registrations, appointments, service reports, and diagnostic procedures.

### User Roles
- **Front Desk Staff**: Handles customer registrations, schedules appointments, and manages billing/discounts.
- **Mechanics**: Creates Service Reports detailing repairs and parts used.
- **Technicians**: Generates Diagnostic Reports identifying vehicle issues and recommended actions.

---

## 2. Design Patterns & Principles

### Creational Patterns
- **Singleton (CMRCenterApp)**:
  - **Implementation**: Ensures a single shared instance for managing customers, cars, appointments, and staff.
  - **Advantages**: Provides a global access point (`getInstance()`) and ensures data consistency.
- **Factory Method (CustomerCreator)**:
  - **Implementation**: Abstract `CustomerCreator` with concrete subclasses (`PrivateCustomerCreator`, `FleetCustomerCreator`, `StaffCustomerCreator`).
  - **Advantages**: Decouples client code from concrete classes and adheres to the **Open-Closed Principle**.

### Structural Patterns
- **Facade (FrontDeskFacade)**:
  - **Implementation**: Hides complex subsystems (scheduler, billing) behind a simplified interface for Front Desk tasks.
  - **Advantages**: Lowers coupling and simplifies the API for common operations.

### Behavioral Patterns
- **Strategy (PaymentStrategy)**:
  - **Implementation**: Allows swapping payment methods (`CashPaymentStrategy`, `CliqPaymentStrategy`, `MobileWalletPaymentStrategy`) at runtime.
  - **Advantages**: New payment methods can be added without modifying the core billing logic.

---

## 3. SOLID Principles Assessment

- **S - Single Responsibility Principle (SRP)**:
  - `AppointmentScheduler` handles only scheduling logic.
  - `CustomerCreator` focuses solely on registering customers.
- **O - Open-Closed Principle (OCP)**:
  - New customer types or payment strategies can be added by creating new subclasses without modifying existing code.
- **L - Liskov Substitution Principle (LSP)**:
  - Any `CustomerCreator` subclass can be used in place of the base class in `FrontDeskFacade`.
  - Any `PaymentStrategy` can be used in `PaymentContext` without affecting correctness.
- **I - Interface Segregation Principle (ISP)**:
  - The `PaymentStrategy` interface defines only one method: `process(double)`.
  - Technicians only interact with `DiagnosticReport` methods, while Mechanics interact with `ServiceReport`.
- **D - Dependency Inversion Principle (DIP)**:
  - High-level modules (`Appointment`, `PaymentContext`) depend on the `PaymentStrategy` interface rather than concrete classes.

---

## 4. Test Plan & Implementation

### Functionalities Tested
1. **Schedule Appointment**: Validates time slots (9:00 - 17:00), current date restriction, and overlap prevention.
2. **Register Customer & Car**: Verifies correct instantiation of different customer types and vehicle storage.
3. **Generate Bill**: Validates fee calculation, discount application (Staff/Fleet), and error handling for missing appointments.

### Automated Testing Methods
- **Unit Testing**: Focused on individual components using **JUnit**.
- **Integration Testing**: Verifies interactions between modules (e.g., Facade and Scheduler).
- **Functional Testing**: Validates end-to-end user scenarios.

---

## 5. References
- Apache JMeter User Manual.
- TestNG Documentation.
- Cucumber Documentation.
- JUnit 5 User Guide.

---
**Authors**: Amir Marmash & Sultan Alrushdan
**Course**: Advanced Programming Final Project
