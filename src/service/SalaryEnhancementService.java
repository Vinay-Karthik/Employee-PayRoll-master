package service;

import decorator.component.BaseSalary;
import decorator.component.SalaryComponent;
import decorator.decorator.HraDecorator;
import decorator.decorator.InsuranceDeductionDecorator;
import decorator.decorator.PerformanceBonusDecorator;
import decorator.decorator.TransportAllowanceDecorator;
import ui.ConsoleUI;

import java.math.BigDecimal;

public class SalaryEnhancementService {
   private final ConsoleUI ui;

   public SalaryEnhancementService(ConsoleUI ui) {
      this.ui = ui;
   }

   public void runDemo() {
      BigDecimal base = ui.readBigDecimal("Base Salary: ");

      SalaryComponent salary = new BaseSalary(base);

      ui.printLine("\nApply enhancements (enter amount or press Enter for default):");

      BigDecimal defaultHra = base.multiply(new BigDecimal("0.20"));
      String input = ui.readLine(String.format("HRA (default %,.2f): ", defaultHra)).trim();
      salary = input.isEmpty()
              ? new HraDecorator(salary)
              : new HraDecorator(salary, new BigDecimal(input));

      BigDecimal defaultTransport = new BigDecimal("1600");
      input = ui.readLine(String.format("Transport (default %,.2f): ", defaultTransport)).trim();
      salary = input.isEmpty()
              ? new TransportAllowanceDecorator(salary)
              : new TransportAllowanceDecorator(salary, new BigDecimal(input));

      BigDecimal defaultBonus = base.multiply(new BigDecimal("0.10"));
      input = ui.readLine(String.format("Bonus (default %,.2f): ", defaultBonus)).trim();
      salary = input.isEmpty()
              ? new PerformanceBonusDecorator(salary)
              : new PerformanceBonusDecorator(salary, new BigDecimal(input));

      BigDecimal defaultInsurance = new BigDecimal("150");
      input = ui.readLine(String.format("Insurance (default %,.2f): ", defaultInsurance)).trim();
      salary = input.isEmpty()
              ? new InsuranceDeductionDecorator(salary)
              : new InsuranceDeductionDecorator(salary, new BigDecimal(input));

      ui.printHeader("SALARY ENHANCEMENT SUMMARY");
      ui.printLine(salary.getDescription());
      ui.printSeparator();
      ui.printLine(String.format("  Base Salary    : Rs. %,.2f", base));
      ui.printLine(String.format("  Final Salary   : Rs. %,.2f", salary.calculateSalary()));
      ui.printLine("=".repeat(60));
   }
}