package service;

import decorator.pipeline.SalaryEnhancementPipeline;
import decorator.pipeline.SalaryPipelineBuilder;
import ui.ConsoleUI;

import java.math.BigDecimal;

public class SalaryEnhancementService {
   private final ConsoleUI ui;

   public SalaryEnhancementService(ConsoleUI ui) {
      this.ui = ui;
   }

   public void runDemo() {
      BigDecimal base = ui.readBigDecimal("Base Salary: ");

      SalaryEnhancementPipeline pipeline = new SalaryPipelineBuilder()
              .baseSalary(base)
              .withDefaults()
              .build();

      ui.printLine("\nApply enhancements (enter amount or press Enter for default):");

      applyWithDefault(pipeline::applyHRA, () -> {
         pipeline.applyHRA();
         return null;
      }, "HRA", base.multiply(new BigDecimal("0.20")));
      applyWithDefault(pipeline::applyTransport, () -> {
         pipeline.applyTransport();
         return null;
      }, "Transport", new BigDecimal("1600"));
      applyWithDefault(pipeline::applyBonus, () -> {
         pipeline.applyBonus();
         return null;
      }, "Bonus", base.multiply(new BigDecimal("0.10")));
      applyWithDefault(pipeline::applyInsurance, () -> {
         pipeline.applyInsurance();
         return null;
      }, "Insurance", new BigDecimal("150"));

      ui.printHeader("SALARY ENHANCEMENT SUMMARY");
      ui.printLine(pipeline.getDescription());
      ui.printSeparator();
      ui.printLine(String.format("  Base Salary    : Rs. %,.2f", base));
      ui.printLine(String.format("  Final Salary   : Rs. %,.2f", pipeline.getCurrentSalary()));
      ui.printLine("=".repeat(60));
   }

   private void applyWithDefault(java.util.function.Consumer<BigDecimal> withAmount,
                                 java.util.function.Supplier<Void> withDefault,
                                 String name, BigDecimal defaultValue) {
      String prompt = String.format("%s (default %,.2f): ", name, defaultValue);
      String input = ui.readLine(prompt).trim();
      if (!input.isEmpty()) {
         withAmount.accept(new BigDecimal(input));
      } else {
         withDefault.get();
      }
   }
}