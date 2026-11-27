package service;

import factory.ContractorData;
import factory.EmployeeFactory;
import factory.FixedContractorData;
import factory.FullTimeData;
import model.Employee;
import model.EmployeeType;
import model.TaxRegime;
import ui.ConsoleUI;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeCreator {
   private final EmployeeFactory factory;
   private final ConsoleUI ui;
   private final List<String> departments;

   public EmployeeCreator(EmployeeFactory factory, ConsoleUI ui, List<String> departments) {
      this.factory = factory;
      this.ui = ui;
      this.departments = departments;
   }

   public Employee createEmployee() {
      EmployeeType type = selectEmployeeType();
      int id = ui.readInt("ID: ");
      String name = ui.readLine("Name: ");
      String dept = selectDepartment();
      String bank = ui.readLine("Bank Account: ");

      return switch (type) {
         case FULL_TIME -> createFullTime(id, name, dept, bank);
         case CONTRACTOR -> createContractor(id, name, dept, bank);
         case FIXED_CONTRACTOR -> createFixedContractor(id, name, dept, bank);
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

   private String selectDepartment() {
      return ui.selectFromList("Department:", departments, d -> d);
   }

   private Employee createFullTime(int id, String name, String dept, String bank) {
      BigDecimal base = ui.readBigDecimal("Base Salary: ");
      BigDecimal bonus = ui.readBigDecimal("Bonus: ");
      TaxRegime regime = readTaxRegime();
      return createFullTime(id, name, dept, bank, base, bonus, regime);
   }

   private Employee createContractor(int id, String name, String dept, String bank) {
      BigDecimal rate = ui.readBigDecimal("Hourly Rate: ");
      BigDecimal hours = ui.readBigDecimal("Hours Worked: ");
      TaxRegime regime = readTaxRegime();
      return createContractor(id, name, dept, bank, rate, hours, regime);
   }

   private Employee createFixedContractor(int id, String name, String dept, String bank) {
      BigDecimal fixed = ui.readBigDecimal("Fixed Contract Amount: ");
      TaxRegime regime = readTaxRegime();
      return createFixedContractor(id, name, dept, bank, fixed, regime);
   }

   // Core creation methods (no UI) - used by both interactive and seed
   private Employee createFullTime(int id, String name, String dept, String bank,
                                   BigDecimal base, BigDecimal bonus, TaxRegime regime) {
      Employee e = factory.create(EmployeeType.FULL_TIME,
              new FullTimeData(id, name, dept, bank, base, bonus));
      e.setTaxRegime(regime);
      ui.printLine("Added Full-Time employee.");
      return e;
   }

   private Employee createContractor(int id, String name, String dept, String bank,
                                     BigDecimal rate, BigDecimal hours, TaxRegime regime) {
      Employee e = factory.create(EmployeeType.CONTRACTOR,
              new ContractorData(id, name, dept, bank, rate, hours));
      e.setTaxRegime(regime);
      ui.printLine("Added Contractor (hourly).");
      return e;
   }

   private Employee createFixedContractor(int id, String name, String dept, String bank,
                                          BigDecimal fixed, TaxRegime regime) {
      Employee e = factory.create(EmployeeType.FIXED_CONTRACTOR,
              new FixedContractorData(id, name, dept, bank, fixed));
      e.setTaxRegime(regime);
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

   // Seed methods - delegate to core creation methods
   public Employee createFullTimeForSeed(int id, String name, String dept, String bank, BigDecimal base, BigDecimal bonus) {
      return createFullTime(id, name, dept, bank, base, bonus, TaxRegime.NEW);
   }

   public Employee createContractorForSeed(int id, String name, String dept, String bank, BigDecimal fixed) {
      return createFixedContractor(id, name, dept, bank, fixed, TaxRegime.NEW);
   }

   public Employee createHourlyContractorForSeed(int id, String name, String dept, String bank, BigDecimal rate, BigDecimal hours) {
      return createContractor(id, name, dept, bank, rate, hours, TaxRegime.NEW);
   }
}