import model.*;
import factory.EmployeeFactory;
import repository.EmployeeRepository;
import repository.InMemoryEmployeeRepository;
import service.PayrollService;
import service.PayrollServiceImpl;
import policy.tax.TaxPolicyRegistry;
import policy.deduction.DeductionPolicyRegistry;
import decorator.pipeline.SalaryEnhancementPipeline;
import decorator.pipeline.SalaryPipelineBuilder;

import java.math.BigDecimal;
import java.util.Scanner;

public class PayrollSystem {
    private static final PayrollService service;
    private static final Scanner sc = new Scanner(System.in);
    private static final InMemoryEmployeeRepository repo = new InMemoryEmployeeRepository();
    private static final EmployeeFactory factory = new EmployeeFactory();

    static {
        service = new PayrollServiceImpl(repo);
        seedData();
    }

    public static void main(String[] args) {
        System.out.println("=== Employee Payroll System (SOLID) ===");
        boolean running = true;

        while (running) {
            printMenu();
            String input = sc.nextLine().trim();
            switch (input) {
                case "1" -> listEmployees();
                case "2" -> addFullTime();
                case "3" -> addContractor();
                case "4" -> addContractorFixed();
                case "5" -> viewPaySlip();
                case "6" -> runFullPayroll();
                case "7" -> salaryEnhancementDemo();
                case "8" -> deleteEmployee();
                case "9" -> { running = false; System.out.println("Goodbye!"); }
                default -> System.out.println("Invalid option.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("""
            \n--- MENU ---
            1. List Employees
            2. Add Full-Time Employee
            3. Add Contractor (Hourly)
            4. Add Contractor (Fixed Contract)
            5. View Pay Slip
            6. Run Full Payroll
            7. Salary Enhancement Demo
            8. Delete Employee
            9. Exit
            Choice: """);
    }

    private static void seedData() {
        service.registerEmployee(factory.createFullTime(1, "Alice Johnson", "Engineering", "BANK001",
            new BigDecimal("6500"), new BigDecimal("800")));
        service.registerEmployee(factory.createContractorFixed(2, "Bob Smith", "IT Consulting", "BANK002",
            new BigDecimal("5000")));
        service.registerEmployee(factory.createContractor(3, "Carol Davis", "Operations", "BANK003",
            new BigDecimal("75"), new BigDecimal("60")));
    }

    private static void listEmployees() {
        var emps = service.getAllEmployees();
        if (emps.isEmpty()) { System.out.println("No employees."); return; }
        System.out.printf("%-5s %-15s %-15s %-10s\n", "ID", "Name", "Department", "Type");
        for (Employee e : emps) {
            System.out.printf("%-5d %-15s %-15s %-10s\n", e.getId(), e.getName(), e.getDepartment(), e.getType().label);
        }
    }

    private static void addFullTime() {
        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Department: "); String dept = sc.nextLine();
        System.out.print("Bank Account: "); String bank = sc.nextLine();
        System.out.print("Base Salary: "); BigDecimal base = new BigDecimal(sc.nextLine());
        System.out.print("Bonus: "); BigDecimal bonus = new BigDecimal(sc.nextLine());
        System.out.print("Tax Regime (OLD/NEW): "); TaxRegime regime = TaxRegime.valueOf(sc.nextLine().toUpperCase());

        Employee e = factory.createFullTime(id, name, dept, bank, base, bonus);
        e.setTaxRegime(regime);
        service.registerEmployee(e);
        System.out.println("Added Full-Time employee.");
    }

    private static void addContractor() {
        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Department: "); String dept = sc.nextLine();
        System.out.print("Bank Account: "); String bank = sc.nextLine();
        System.out.print("Hourly Rate: "); BigDecimal rate = new BigDecimal(sc.nextLine());
        System.out.print("Hours Worked: "); BigDecimal hours = new BigDecimal(sc.nextLine());
        System.out.print("Tax Regime (OLD/NEW): "); TaxRegime regime = TaxRegime.valueOf(sc.nextLine().toUpperCase());

        Employee e = factory.createContractor(id, name, dept, bank, rate, hours);
        e.setTaxRegime(regime);
        service.registerEmployee(e);
        System.out.println("Added Contractor (hourly).");
    }

    private static void addContractorFixed() {
        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Department: "); String dept = sc.nextLine();
        System.out.print("Bank Account: "); String bank = sc.nextLine();
        System.out.print("Fixed Contract Amount: "); BigDecimal fixed = new BigDecimal(sc.nextLine());
        System.out.print("Tax Regime (OLD/NEW): "); TaxRegime regime = TaxRegime.valueOf(sc.nextLine().toUpperCase());

        Employee e = factory.createContractorFixed(id, name, dept, bank, fixed);
        e.setTaxRegime(regime);
        service.registerEmployee(e);
        System.out.println("Added Contractor (fixed).");
    }

    private static void viewPaySlip() {
        System.out.print("Employee ID: "); int id = Integer.parseInt(sc.nextLine());
        service.getEmployeeById(id).ifPresentOrElse(
            e -> System.out.println(e.generatePaySlip()),
            () -> System.out.println("Employee not found.")
        );
    }

    private static void runFullPayroll() {
        var slips = service.processAllPaySlips();
        if (slips.isEmpty()) { System.out.println("No payroll data."); return; }

        BigDecimal totalGross = BigDecimal.ZERO, totalTax = BigDecimal.ZERO;
        BigDecimal totalDed = BigDecimal.ZERO, totalNet = BigDecimal.ZERO;

        System.out.println("=".repeat(90));
        System.out.printf("%-5s %-15s %-12s %-12s %-12s %-12s %-12s\n", "ID", "Name", "Type", "Gross", "Tax", "Deduct", "Net");
        System.out.println("-".repeat(90));

        for (PaySlip s : slips) {
            totalGross = totalGross.add(s.grossPay());
            totalTax = totalTax.add(s.taxWithholding());
            totalDed = totalDed.add(s.deductions());
            totalNet = totalNet.add(s.netPay());

            System.out.printf("%-5d %-15s %-12s $%-11.2f $%-11.2f $%-11.2f $%-11.2f\n",
                s.employeeId(), s.employeeName(), s.roleCategory(),
                s.grossPay(), s.taxWithholding(), s.deductions(), s.netPay());
        }

        System.out.println("=".repeat(90));
        System.out.printf("TOTALS: Gross $%.2f | Tax $%.2f | Deductions $%.2f | Net $%.2f\n", totalGross, totalTax, totalDed, totalNet);
        System.out.println("=".repeat(90));
    }

    private static void salaryEnhancementDemo() {
        System.out.print("Base Salary: "); BigDecimal base = new BigDecimal(sc.nextLine());
        
        SalaryEnhancementPipeline p = new SalaryPipelineBuilder()
            .baseSalary(base)
            .withDefaults()
            .build();
        
        System.out.println("Apply enhancements (y/n):");
        System.out.print("HRA (20% base)? "); if (sc.nextLine().equalsIgnoreCase("y")) p.applyHRA();
        System.out.print("Transport ($1600)? "); if (sc.nextLine().equalsIgnoreCase("y")) p.applyTransport();
        System.out.print("Bonus (10% base)? "); if (sc.nextLine().equalsIgnoreCase("y")) p.applyBonus();
        System.out.print("Insurance ($150)? "); if (sc.nextLine().equalsIgnoreCase("y")) p.applyInsurance();
        System.out.println("Result: " + p.getDescription());
        System.out.println("Final Salary: $" + p.getCurrentSalary());
    }

    private static void deleteEmployee() {
        System.out.print("Employee ID: "); int id = Integer.parseInt(sc.nextLine());
        System.out.println(service.removeEmployee(id) ? "Deleted." : "Not found.");
    }
}
