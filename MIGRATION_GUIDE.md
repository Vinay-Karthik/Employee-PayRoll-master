# Migration Guide: Employee Payroll System Refactoring

This guide outlines the changes made during the refactoring process for developers maintaining or extending the Employee Payroll System.

---

## 1. Package & Class Relocations

### Decorator Pattern Package Flattening
The three nested sub-packages under `decorator/` have been flattened into a single, cohesive `decorator` package:

| Previous Fully Qualified Name | New Fully Qualified Name | Notes |
| :--- | :--- | :--- |
| `decorator.component.SalaryComponent` | `decorator.SalaryComponent` | Base component interface |
| `decorator.component.BaseSalaryCalculator` | `decorator.BaseSalaryCalculator` | Concrete component |
| `decorator.decorator.SalaryDecorator` | `decorator.SalaryDecorator` | Abstract decorator base class |
| `decorator.decorator.HraDecorator` | `decorator.HraDecorator` | Concrete decorator |
| `decorator.decorator.TransportAllowanceDecorator` | `decorator.TransportAllowanceDecorator` | Concrete decorator |
| `decorator.decorator.PerformanceBonusDecorator` | `decorator.PerformanceBonusDecorator` | Concrete decorator |
| `decorator.decorator.InsuranceDeductionDecorator` | `decorator.InsuranceDeductionDecorator` | Concrete decorator |
| `decorator.pipeline.DecoratorType` | `decorator.DecoratorType` | Enum of enhancement types |
| `decorator.pipeline.SalaryEnhancementPipeline` | `decorator.SalaryEnhancementPipeline` | Orchestrating builder/pipeline |

#### How to update your code:
Change any imports from:
```java
import decorator.component.*;
import decorator.decorator.*;
import decorator.pipeline.*;
```
to:
```java
import decorator.*;
```

---

## 2. New Classes & Constants

### `common.PayrollConstants`
Centralized application-wide constants to eliminate duplicate literal strings across components:
- `PayrollConstants.DEFAULT_CURRENCY` (`"USD"`)
- `PayrollConstants.DEFAULT_FINANCIAL_YEAR` (`"FY2025-26"`)

Usage:
```java
import common.PayrollConstants;

String currency = PayrollConstants.DEFAULT_CURRENCY;
String fy = PayrollConstants.DEFAULT_FINANCIAL_YEAR;
```

---

## 3. Build & Test System

### Maven Integration
The project now includes a standard Maven `pom.xml` configured for **Java 17** and **JUnit 5**.

- **Run all automated tests:**
  ```bash
  mvn test
  ```
- **Compile project:**
  ```bash
  mvn compile
  ```
- **Run application:**
  ```bash
  mvn exec:java -Dexec.mainClass="PayrollSystem"
  ```

---

## 4. Preserved Core APIs (No Breaking Changes)

The following public contracts remain completely backward-compatible:
1. `Employee` and all subclasses (`FullTimeEmployee`, `PartTimeEmployee`, `ContractorEmployee`, `InternEmployee`).
2. `PayrollService` and `PayrollServiceImpl`.
3. `PayrollFacade` (method signatures and behavior unchanged).
4. `ExternalHRAdapter` and `ExternalHRService`.
5. `TaxPolicy` and `DeductionPolicy` (including OLD/NEW regime calculation logic).
6. `PayrollRequest` builder and calculation flow.
