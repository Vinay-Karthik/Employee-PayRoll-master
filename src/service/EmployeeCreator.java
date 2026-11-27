package service;

import model.Employee;
import model.FullTimeEmployee;
import model.ContractorEmployee;
import model.enums.Department;
import model.enums.EmployeeType;
import model.enums.TaxRegime;
import ui.ConsoleUI;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeCreator {
    private final ConsoleUI ui;
    private final List<Department> departments;

    public EmployeeCreator(ConsoleUI ui, List<Department> departments) {
        this.ui = ui;
        this.departments = departments;
    }

    public Employee createEmployee() {
        EmployeeType type = selectEmployeeType();
        int id = ui.readInt("ID: ");
        String name = ui.readLine("Name: ");
        String dept = selectDepartment().getDisplayName();
        String bank = ui.readLine("Bank Account: ");
        TaxRegime regime = readTaxRegime();

        return switch (type) {
            case FULL_TIME -> createFullTime(id, name, dept, bank, regime);
            case CONTRACTOR -> createContractor(id, name, dept, bank, regime);
            case FIXED_CONTRACTOR -> createFixedContractor(id, name, dept, bank, regime);
        };
    }

    private EmployeeType selectEmployeeType() {
        System.out.println("Employee Type:");
        System.out.println("  1. Full-Time Employee");
        System.out.println("  2. Contractor (Hourly)");
        System.out.println("  3. Contractor (Fixed Contract)");
        int choice = ui.readInt("Choose (1-3): ");
        return switch (choice) {
            case 1 -> EmployeeType.FULL_TIME;
            case 2 -> EmployeeType.CONTRACTOR;
            case 3 -> EmployeeType.FIXED_CONTRACTOR;
            default -> {
                System.out.println("Invalid choice, defaulting to Full-Time.");
                yield EmployeeType.FULL_TIME;
            }
        };
    }

    private Department selectDepartment() {
        return ui.selectFromList("Department:", departments, Department::getDisplayName);
    }

    private Employee createFullTime(int id, String name, String dept, String bank, TaxRegime regime) {
        BigDecimal base = ui.readBigDecimal("Base Salary: ");
        BigDecimal bonus = ui.readBigDecimal("Bonus: ");
        Employee e = new FullTimeEmployee.Builder()
                .id(id)
                .name(name)
                .department(dept)
                .bankAccount(bank)
                .baseSalary(base)
                .bonus(bonus)
                .taxRegime(regime)
                .build();
        ui.printLine("Added Full-Time employee.");
        return e;
    }

    private Employee createContractor(int id, String name, String dept, String bank, TaxRegime regime) {
        BigDecimal rate = ui.readBigDecimal("Hourly Rate: ");
        BigDecimal hours = ui.readBigDecimal("Hours Worked: ");
        Employee e = new ContractorEmployee.Builder()
                .id(id)
                .name(name)
                .department(dept)
                .bankAccount(bank)
                .hourlyRate(rate)
                .hoursWorked(hours)
                .taxRegime(regime)
                .build();
        ui.printLine("Added Contractor (hourly).");
        return e;
    }

    private Employee createFixedContractor(int id, String name, String dept, String bank, TaxRegime regime) {
        BigDecimal fixed = ui.readBigDecimal("Fixed Contract Amount: ");
        Employee e = new ContractorEmployee.Builder()
                .id(id)
                .name(name)
                .department(dept)
                .bankAccount(bank)
                .fixedContractAmount(fixed)
                .taxRegime(regime)
                .build();
        ui.printLine("Added Contractor (fixed).");
        return e;
    }

    private TaxRegime readTaxRegime() {
        String input = ui.readLine("Tax Regime (OLD/NEW): ").toUpperCase();
        try {
            return TaxRegime.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid regime, defaulting to NEW.");
            return TaxRegime.NEW;
        }
    }

    // Seed methods - use Builder pattern
    public Employee createFullTimeForSeed(int id, String name, String dept, String bank, BigDecimal base, BigDecimal bonus) {
        return new FullTimeEmployee.Builder()
                .id(id)
                .name(name)
                .department(dept)
                .bankAccount(bank)
                .baseSalary(base)
                .bonus(bonus)
                .taxRegime(TaxRegime.NEW)
                .build();
    }

    public Employee createContractorForSeed(int id, String name, String dept, String bank, BigDecimal fixed) {
        return new ContractorEmployee.Builder()
                .id(id)
                .name(name)
                .department(dept)
                .bankAccount(bank)
                .fixedContractAmount(fixed)
                .taxRegime(TaxRegime.NEW)
                .build();
    }

    public Employee createHourlyContractorForSeed(int id, String name, String dept, String bank, BigDecimal rate, BigDecimal hours) {
        return new ContractorEmployee.Builder()
                .id(id)
                .name(name)
                .department(dept)
                .bankAccount(bank)
                .hourlyRate(rate)
                .hoursWorked(hours)
                .taxRegime(TaxRegime.NEW)
                .build();
    }
}