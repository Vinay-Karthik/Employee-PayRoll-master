# Enterprise Payroll Application (Refactored)

A clean, modular, and maintainable Java Employee Payroll Management System adhering strictly to **SOLID design principles**, clear separation of concerns, and clean architectural design patterns.

---

## 🏗️ Architecture & Package Structure

Packages are organized by **business domain and architectural responsibility**:

```
Employee-PayRoll/
├── pom.xml                            # Maven project build file (Java 17+, JUnit 5)
├── src/
│   ├── common/                        # Shared application constants
│   │   └── PayrollConstants.java      # Currency & financial year constants
│   ├── model/                         # Domain entities, value objects & request models
│   │   ├── entity/                    # Core employee domain models
│   │   │   ├── Employee.java          # Abstract base domain employee (Template Method Pattern)
│   │   │   ├── FullTimeEmployee.java  # Salaried full-time employee
│   │   │   ├── PartTimeEmployee.java  # Hourly part-time employee with overtime rules
│   │   │   ├── ContractorEmployee.java# Billed contractor with service fee withholding
│   │   │   ├── InternEmployee.java    # Intern with stipend tax exemption limits
│   │   │   └── EmployeeType.java      # Unified employment categories enum
│   │   ├── factory/                   # Employee construction factories
│   │   │   ├── EmployeeFactory.java   # Factory interface for interactive creation
│   │   │   ├── FullTimeEmployeeFactory.java
│   │   │   ├── PartTimeEmployeeFactory.java
│   │   │   ├── ContractorEmployeeFactory.java
│   │   │   ├── InternEmployeeFactory.java
│   │   │   └── EmployeeDetails.java
│   │   ├── request/                   # Builder-constructed immutable payroll run request
│   │   │   └── PayrollRequest.java
│   │   ├── util/                      # Reusable calculation algorithms
│   │   │   └── GrossPayCalculator.java# Gross salary calculation formulas
│   │   └── value/                     # Value objects & immutable DTOs
│   │       ├── PaySlip.java           # Immutable record DTO for calculation results
│   │       └── SalaryComponents.java  # Monetary salary components
│   ├── calculator/                    # Salary calculation engines
│   │   ├── core/                      # SalaryCalculator interface
│   │   ├── factory/                   # SalaryCalculatorFactory hierarchy
│   │   └── impl/                      # Calculation implementations per employee type
│   ├── decorator/                     # Flattened dynamic salary enhancement (Decorator Pattern)
│   │   ├── SalaryComponent.java       # Component interface
│   │   ├── BaseSalaryCalculator.java  # Concrete component
│   │   ├── SalaryDecorator.java       # Abstract base decorator
│   │   ├── HraDecorator.java          # House Rent Allowance decorator
│   │   ├── TransportAllowanceDecorator.java # Transport allowance decorator
│   │   ├── PerformanceBonusDecorator.java   # Performance bonus decorator
│   │   ├── InsuranceDeductionDecorator.java # Insurance deduction decorator
│   │   ├── DecoratorType.java         # Type-safe enum for enhancement components
│   │   └── SalaryEnhancementPipeline.java # Domain pipeline managing stacking & business rules
│   ├── tax/                           # Tax and deduction policies (Strategy Pattern)
│   │   ├── policy/                    # Strategy interfaces
│   │   │   ├── TaxPolicy.java         # Tax calculation strategy interface (OLD & NEW regime support)
│   │   │   └── DeductionPolicy.java   # Deduction calculation strategy interface
│   │   ├── impl/                      # Concrete strategies per employee category
│   │   │   ├── FullTimeTaxPolicy.java / FullTimeDeductionPolicy.java
│   │   │   ├── PartTimeTaxPolicy.java / PartTimeDeductionPolicy.java
│   │   │   ├── ContractorTaxPolicy.java / ContractorDeductionPolicy.java
│   │   │   └── InternTaxPolicy.java / InternDeductionPolicy.java
│   │   └── regime/                    # Tax regime enum (OLD, NEW)
│   │       └── TaxRegime.java
│   ├── integration/                   # External systems integration (Adapter Pattern)
│   │   ├── ExternalHRAdapter.java     # Adapter adapting external HR service to EmployeeRepository
│   │   ├── ExternalHRService.java     # Third-party HR API simulation (Adaptee)
│   │   ├── HRRecord.java              # External DTO
│   │   ├── ExternalEmployeeNotFoundException.java
│   │   └── ExternalServiceException.java
│   ├── repository/                    # Persistence abstractions & in-memory stores (Repository Pattern)
│   │   ├── EmployeeRepository.java
│   │   └── InMemoryEmployeeRepository.java
│   ├── service/                       # Core payroll business workflow services
│   │   ├── PayrollService.java        # Core business interface
│   │   ├── PayrollServiceImpl.java    # Primary implementation
│   │   └── PayrollFacade.java         # Facade orchestrating monthly payroll processing
│   ├── ui/                            # Presentation layer & CLI controllers (Command Pattern)
│   │   ├── cli/
│   │   │   └── PayrollCLI.java        # Main console UI coordinator
│   │   ├── command/                   # Command pattern menu handlers
│   │   └── view/
│   │       └── ConsoleView.java       # Formatted console outputs
│   ├── test/java/                     # Automated JUnit 5 Unit Tests
│   │   ├── EmployeeCalculationTest.java # Gross, tax, deductions & net pay tests
│   │   ├── DecoratorPipelineTest.java   # Dynamic enhancement pipeline tests
│   │   ├── PayrollServiceTest.java      # Service, facade & repository lifecycle tests
│   │   └── ExternalHRAdapterTest.java   # External HR integration tests
│   └── PayrollSystem.java             # Composition Root: Dependency Injection entry point
```

---

## 🎨 Design Patterns Retained & Simplified

| Pattern | Package | Key Classes | Why Retained & How Simplified |
| :--- | :--- | :--- | :--- |
| **Decorator** | `decorator` | `SalaryComponent`, `BaseSalaryCalculator`, `HraDecorator`, `TransportAllowanceDecorator`, `PerformanceBonusDecorator`, `InsuranceDeductionDecorator`, `SalaryEnhancementPipeline`, `DecoratorType` | **Simplified**: Flattened from 3 nested sub-packages (`component`, `decorator`, `pipeline`) into one clean package. Retained because it provides dynamic, ordered, and customizable salary additions and deductions at runtime. |
| **Strategy** | `tax` | `TaxPolicy`, `DeductionPolicy`, concrete `*TaxPolicy` and `*DeductionPolicy` classes | **Retained**: Keeps tax calculation independent from employee domain models and cleanly supports both **OLD** and **NEW** tax regimes without sprawling conditionals. |
| **Adapter** | `integration` | `ExternalHRAdapter`, `ExternalHRService`, `EmployeeRepository` | **Retained**: Decouples third-party HR data representations from internal domain structures. |
| **Builder** | `model.request`| `PayrollRequest.Builder` | **Retained**: Clean construction of complex payroll calculation requests with defensive validation. |
| **Facade** | `service` | `PayrollFacade` | **Retained as clean wrapper**: Coordinates the multi-step monthly payroll generation and reporting flow. |
| **Repository** | `repository` | `EmployeeRepository`, `InMemoryEmployeeRepository` | **Retained**: Decouples domain storage from memory vs persistent database mechanisms. |
| **Command** | `ui.command` | `MenuCommand`, `AddEmployeeCommand`, `FullPayrollCommand`, etc. | **Retained**: Keeps CLI menu options organized, testable, and cleanly isolated. |

---

## 💡 Dynamic Salary Enhancement (Decorator Pattern)

Decorators wrap `SalaryComponent` instances in any sequence. Business rules (single-use validation, deduction caps against accumulated salary) are encapsulated in `SalaryEnhancementPipeline`:

```java
// 1. Initialize Pipeline with Base Salary
SalaryEnhancementPipeline pipeline = new SalaryEnhancementPipeline(new BigDecimal("5000.00"));

// 2. Fluently apply decorators (defaults or custom amounts)
pipeline.applyDecorator(DecoratorType.HRA)                   // +20% base ($1,000.00)
        .applyDecorator(DecoratorType.TRANSPORT_ALLOWANCE)   // +$1,600.00
        .applyDecorator(DecoratorType.PERFORMANCE_BONUS)     // +10% base ($500.00)
        .applyDecorator(DecoratorType.INSURANCE_DEDUCTION);  // -$150.00

BigDecimal total = pipeline.getCurrentSalary();              // $7,950.00
String details = pipeline.getCurrentDescription();
```

---

## 🚀 Building & Running the Application

### Prerequisites
- **Java 17** or higher
- **Maven 3.6+**

### 1. Build & Run Tests
```bash
# Run all automated tests (JUnit 5)
mvn clean test
```

### 2. Compile the Application
```bash
mvn compile
```

### 3. Run the CLI Application
Using Maven:
```bash
mvn exec:java -Dexec.mainClass="PayrollSystem"
```
Or with standard Java commands:
```bash
# Compile
javac -d target/classes $(Get-ChildItem -Path src -Recurse -Filter *.java | Where-Object { $_.FullName -notmatch '\\test\\' } | ForEach-Object { $_.FullName })

# Run
java -cp target/classes PayrollSystem
```
