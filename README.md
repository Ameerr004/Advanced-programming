# Car Maintenance and Repair Center Management System (CMR System)

## Project Overview
The **Car Maintenance and Repair Center Management System (CMR System)** is a Java-based application designed to streamline the operations of a vehicle service center. It handles customer registrations, appointment scheduling, service and diagnostic reporting, and billing processes.

The system is designed with a role-based architecture, supporting three primary user roles:
- **Front Desk Staff**: Manages customer and vehicle registration, schedules appointments, and handles billing with applicable discounts.
- **Mechanics**: Responsible for performing repairs and generating **Service Reports** detailing the work done and parts used.
- **Technicians**: Conduct vehicle diagnostics and generate **Diagnostic Reports** identifying issues and recommended actions.

## Key Features
- **Customer Management**: Supports different customer categories (Private, Fleet, and Staff) with specialized registration logic.
- **Appointment Scheduling**: Efficiently manage time slots for vehicle services.
- **Reporting System**: Separate modules for service and diagnostic reports.
- **Billing & Payments**: Integrated payment processing with support for Cash, Mobile Apps, and Cliq.
- **Automated Reporting**: Ability to generate daily summaries of all center activities.

## Software Architecture & Design Patterns
This project demonstrates the application of several advanced software engineering principles and design patterns:

### Design Patterns
- **Singleton Pattern**: Used for the core application instance (`CMRCenterApp`) to ensure consistent data management across the system.
- **Factory Method Pattern**: Implemented for customer creation (`CustomerCreator` hierarchy) to encapsulate registration logic for different customer types.
- **Facade Pattern**: The `FrontDeskFacade` provides a simplified interface to complex subsystems like scheduling and billing.
- **Strategy Pattern**: Employed for payment processing (`PaymentStrategy`), allowing for easy swapping and extension of payment methods at runtime.

### SOLID Principles
- **SRP (Single Responsibility Principle)**: Each class (e.g., `AppointmentScheduler`, `PaymentStrategy`) focuses on a single core functionality.
- **OCP (Open-Closed Principle)**: The system is designed for extension (e.g., adding new customer types or payment methods) without modifying existing code.
- **LSP (Liskov Substitution Principle)**: Abstract classes and interfaces (e.g., `CustomerCreator`, `PaymentStrategy`) can be substituted with any of their concrete implementations without affecting system correctness.
- **ISP (Interface Segregation Principle)**: Interfaces like `Report` and `PaymentStrategy` are kept minimal and focused.
- **DIP (Dependency Inversion Principle)**: High-level modules depend on abstractions rather than concrete implementations.

## Getting Started
### Prerequisites
- Java Development Kit (JDK) 8 or higher.
- IDE (IntelliJ IDEA, Eclipse, or VS Code).

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Ameerr004/Advanced-programming.git
   ```
2. Open the project in your preferred Java IDE.
3. Locate the `Main.java` file in the `Final` package.

### Running the Application
Run the `Main.java` file to launch the command-line interface. Follow the on-screen prompts to select your role and perform various operations.

## Testing
The project includes a suite of automated tests using **JUnit** to ensure system reliability. Key functionalities tested include:
- Appointment scheduling constraints.
- Customer and vehicle registration.
- Billing and discount calculations.

## Authors
- **Amir Marmash** (22110245)
- **Sultan Alrushdan**
- **Course**: Advanced Programming

---
*Developed as a Final Project for Advanced Programming Course.*
