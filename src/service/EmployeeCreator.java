package service;

import model.Employee;
import model.enums.Department;
import model.enums.EmployeeType;
import model.enums.TaxRegime;
import ui.ConsoleUI;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeCreator {
   private final ConsoleUI ui;
   private final List<Department> departments;
   private final EmployeeFactory factory;

   public EmployeeCreator(ConsoleUI ui, List<Department> departments, EmployeeFactory factory) {
      this.ui = ui;
      this.departments = departments;
      this.factory = factory;
   }

   // ==================== Public API ====================

   public Employee createEmployee() {
      EmployeeType type = selectEmployeeType();
      int id = ui.readInt("ID: ");
      String name = ui.readLine("Name: ");
      String dept = selectDepartment().getDisplayName();
      String bank = ui.readLine("Bank Account: ");
      TaxRegime regime = readTaxRegime();

      return switch (type) {
         case FULL_TIME -> createFullTime(id, name, dept, bank, regime);
         case CONTRACTOR -> createHourlyContractor(id, name, dept, bank, regime);
         case FIXED_CONTRACTOR -> createFixedContractor(id, name, dept, bank, regime);
      };
   }

   // ==================== Seed Methods (Delegate to Factory) ====================

   public Employee createFullTimeForSeed(int id, String name, String dept, String bank,
                                         BigDecimal baseSalary, BigDecimal bonus) {
      return factory.createFullTimeForSeed(id, name, dept, bank, baseSalary, bonus);
   }

   public Employee createHourlyContractorForSeed(int id, String name, String dept, String bank,
                                                 BigDecimal hourlyRate, BigDecimal hoursWorked) {
      return factory.createHourlyContractorForSeed(id, name, dept, bank, hourlyRate, hoursWorked);
   }

   public Employee createFixedContractorForSeed(int id, String name, String dept, String bank,
                                                BigDecimal fixedContractAmount) {
      return factory.createFixedContractorForSeed(id, name, dept, bank, fixedContractAmount);
   }

   // ==================== Private Interactive Creation ====================

   private Employee createFullTime(int id, String name, String dept, String bank, TaxRegime regime) {
      BigDecimal baseSalary = ui.readBigDecimal("Base Salary: ");
      BigDecimal bonus = ui.readBigDecimal("Bonus: ");
      Employee e = factory.createFullTime(id, name, dept, bank, baseSalary, bonus, regime);
      ui.printLine("Added Full-Time employee.");
      return e;
   }

   private Employee createHourlyContractor(int id, String name, String dept, String bank, TaxRegime regime) {
      BigDecimal hourlyRate = ui.readBigDecimal("Hourly Rate: ");
      BigDecimal hoursWorked = ui.readBigDecimal("Hours Worked: ");
      Employee e = factory.createHourlyContractor(id, name, dept, bank, hourlyRate, hoursWorked, regime);
      ui.printLine("Added Contractor (hourly).");
      return e;
   }

   private Employee createFixedContractor(int id, String name, String dept, String bank, TaxRegime regime) {
      BigDecimal fixedContractAmount = ui.readBigDecimal("Fixed Contract Amount: ");
      Employee e = factory.createFixedContractor(id, name, dept, bank, fixedContractAmount, regime);
      ui.printLine("Added Contractor (fixed).");
      return e;
   }

   // ==================== UI Helpers ====================

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

   private TaxRegime readTaxRegime() {
      String input = ui.readLine("Tax Regime (OLD/NEW): ").toUpperCase();
      try {
         return TaxRegime.valueOf(input);
      } catch (IllegalArgumentException e) {
         System.out.println("Invalid regime, defaulting to NEW.");
         return TaxRegime.NEW;
      }
   }
}