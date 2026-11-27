package service;

import decorator.pipeline.DecoratorConfig;
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

        for (DecoratorConfig config : DecoratorConfig.DEFAULT_DECORATORS) {
            BigDecimal defaultAmount = config.defaultAmountCalculator().apply(base);
            String prompt = String.format("%s (default %,.2f): ", config.displayName(), defaultAmount);
            
            String input = ui.readLine(prompt).trim();
            if (!input.isEmpty()) {
                pipeline.apply(config.type(), new BigDecimal(input));
            } else {
                pipeline.apply(config.type(), null); // uses default
            }
        }

        ui.printHeader("SALARY ENHANCEMENT SUMMARY");
        ui.printLine(pipeline.getDescription());
        ui.printSeparator();
        ui.printLine(String.format("  Base Salary    : Rs. %,.2f", base));
        ui.printLine(String.format("  Final Salary   : Rs. %,.2f", pipeline.getCurrentSalary()));
        ui.printLine("=".repeat(60));
    }
}