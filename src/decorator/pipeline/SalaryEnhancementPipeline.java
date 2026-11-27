package decorator.pipeline;

import decorator.component.BaseSalary;
import decorator.component.SalaryComponent;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class SalaryEnhancementPipeline {
   private final Set<DecoratorType> applied = EnumSet.noneOf(DecoratorType.class);
   private final Map<DecoratorType, DecoratorFactory> factories;
   private SalaryComponent current;

   public SalaryEnhancementPipeline(BigDecimal baseSalary,
                                    Map<DecoratorType, DecoratorFactory> factories) {
      this.current = new BaseSalary(baseSalary);
      this.factories = Map.copyOf(factories);
   }

   public SalaryEnhancementPipeline apply(DecoratorType type, BigDecimal customAmount) {
      if (applied.contains(type)) {
         throw new IllegalStateException(type + " already applied");
      }

      DecoratorFactory factory = factories.get(type);
      if (factory == null) {
         throw new IllegalStateException("No factory registered for: " + type);
      }

      current = (customAmount != null)
              ? factory.create(current, customAmount)
              : factory.createWithDefault(current);

      applied.add(type);
      return this;
   }

   public SalaryEnhancementPipeline applyHRA() {
      return apply(DecoratorType.HRA, null);
   }

   public SalaryEnhancementPipeline applyHRA(BigDecimal a) {
      return apply(DecoratorType.HRA, a);
   }

   public SalaryEnhancementPipeline applyTransport() {
      return apply(DecoratorType.TRANSPORT, null);
   }

   public SalaryEnhancementPipeline applyTransport(BigDecimal a) {
      return apply(DecoratorType.TRANSPORT, a);
   }

   public SalaryEnhancementPipeline applyBonus() {
      return apply(DecoratorType.BONUS, null);
   }

   public SalaryEnhancementPipeline applyBonus(BigDecimal a) {
      return apply(DecoratorType.BONUS, a);
   }

   public SalaryEnhancementPipeline applyInsurance() {
      return apply(DecoratorType.INSURANCE, null);
   }

   public SalaryEnhancementPipeline applyInsurance(BigDecimal a) {
      return apply(DecoratorType.INSURANCE, a);
   }

   public BigDecimal getCurrentSalary() {
      return current.calculateSalary();
   }

   public BigDecimal getBaseSalary() {
      return current.getBaseSalary();
   }

   public String getDescription() {
      return current.getDescription();
   }

   public boolean isApplied(DecoratorType type) {
      return applied.contains(type);
   }
}