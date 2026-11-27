package app;

import factory.EmployeeFactory;
import model.Employee;
import model.PaySlip;
import policy.deduction.DeductionPolicyRegistry;
import policy.tax.TaxPolicyRegistry;
import report.PayrollReportFormatter;
import repository.EmployeeRepository;
import repository.InMemoryEmployeeRepository;
import service.EmployeeCreator;
import service.PayrollService;
import service.PayrollServiceImpl;
import service.SalaryEnhancementService;
import ui.ConsoleUI;
import ui.MenuOption;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PayrollApplication {
   private final ConsoleUI ui;
   private final PayrollService payrollService;
   private final EmployeeCreator employeeCreator;
   private final SalaryEnhancementService salaryEnhancementService;
   private final PayrollReportFormatter reportFormatter;
   private final List<MenuOption> menuOptions;
   private boolean running = true;

   public PayrollApplication() {
      this.ui = new ConsoleUI();

      EmployeeRepository repository = new InMemoryEmployeeRepository();
      TaxPolicyRegistry taxRegistry = new TaxPolicyRegistry();
      DeductionPolicyRegistry deductionRegistry = new DeductionPolicyRegistry();

      EmployeeFactory factory = new EmployeeFactory(taxRegistry, deductionRegistry);

      this.payrollService = new PayrollServiceImpl(repository);
      this.employeeCreator = new EmployeeCreator(factory, ui, getDepartments());
      this.salaryEnhancementService = new SalaryEnhancementService(ui);
      this.reportFormatter = new PayrollReportFormatter();

      this.menuOptions = createMenuOptions();
      seedData();
   }

   private List<String> getDepartments() {
      return Arrays.asList(
              "Engineering", "IT Consulting", "Operations", "HR", "Finance", "Marketing", "Sales"
      );
   }

   private List<MenuOption> createMenuOptions() {
      return List.of(
              menu("1", "List Employees", this::listEmployees),
              menu("2", "Add Employee", this::addEmployee),
              menu("3", "View Pay Slip", this::viewPaySlip),
              menu("4", "Run Full Payroll", this::runFullPayroll),
              menu("5", "Salary Enhancement Demo", salaryEnhancementService::runDemo),
              menu("6", "Delete Employee", this::deleteEmployee),
              menu("7", "Exit", this::exit)
      );
   }

   private MenuOption menu(String key, String description, Runnable action) {
      return new MenuOption() {
         public String getKey() { return key; }
         public String getDescription() { return description; }
         public void execute() {
            action.run();
         }
      };
   }

   public void run() {
      ui.printLine("=== Employee Payroll System ===");

      while (running) {
         printMenu();
         String input = ui.readLine("Choice: ");
         executeOption(input);
      }

      ui.close();
   }

   private void printMenu() {
      ui.printLine("\n--- MENU ---");
      for (MenuOption option : menuOptions) {
         ui.printLine("  " + option.getKey() + ". " + option.getDescription());
      }
   }

   private void executeOption(String input) {
      for (MenuOption option : menuOptions) {
         if (option.getKey().equals(input)) {
            option.execute();
            return;
         }
      }
      ui.printLine("Invalid option.");
   }

   private void seedData() {
      payrollService.registerEmployee(employeeCreator.createFullTimeForSeed(1, "Alice Johnson", "Engineering", "BANK001", new BigDecimal("6500"), new BigDecimal("800")));
      payrollService.registerEmployee(employeeCreator.createContractorForSeed(2, "Bob Smith", "IT Consulting", "BANK002", new BigDecimal("5000")));
      payrollService.registerEmployee(employeeCreator.createHourlyContractorForSeed(3, "Carol Davis", "Operations", "BANK003", new BigDecimal("75"), new BigDecimal("60")));
   }

   private void listEmployees() {
      var emps = payrollService.getAllEmployees();
      if (emps.isEmpty()) {
         ui.printLine("No employees.");
         return;
      }
      ui.printLine(String.format("%-5s %-15s %-15s %-10s", "ID", "Name", "Department", "Type"));
      for (var e : emps) {
         ui.printLine(String.format("%-5d %-15s %-15s %-10s", e.getId(), e.getName(), e.getDepartment(), e.getType().label));
      }
   }

   private void addEmployee() {
      Employee emp = employeeCreator.createEmployee();
      payrollService.registerEmployee(emp);
   }

   private void viewPaySlip() {
      int id = ui.readInt("Employee ID: ");
      payrollService.getEmployeeById(id).ifPresentOrElse(
              e -> ui.printLine(e.generatePaySlip().toString()),
              () -> ui.printLine("Employee not found.")
      );
   }

   private void runFullPayroll() {
      List<PaySlip> slips = payrollService.processAllPaySlips();
      ui.printLine(reportFormatter.format(slips));
   }

   private void deleteEmployee() {
      int id = ui.readInt("Employee ID: ");
      ui.printLine(payrollService.removeEmployee(id) ? "Deleted." : "Not found.");
   }

   private void exit() {
      running = false;
      ui.printLine("Goodbye!");
   }
}