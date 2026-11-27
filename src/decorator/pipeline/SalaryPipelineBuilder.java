package decorator.pipeline;

import decorator.pipeline.factory.BonusDecoratorFactory;
import decorator.pipeline.factory.HraDecoratorFactory;
import decorator.pipeline.factory.InsuranceDecoratorFactory;
import decorator.pipeline.factory.TransportDecoratorFactory;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class SalaryPipelineBuilder {
   private final Map<DecoratorType, DecoratorFactory> factories = new EnumMap<>(DecoratorType.class);
   private BigDecimal baseSalary;

   public SalaryPipelineBuilder baseSalary(BigDecimal baseSalary) {
      this.baseSalary = baseSalary;
      return this;
   }

   public SalaryPipelineBuilder withDefaults() {
      factories.put(DecoratorType.HRA, new HraDecoratorFactory());
      factories.put(DecoratorType.TRANSPORT, new TransportDecoratorFactory());
      factories.put(DecoratorType.BONUS, new BonusDecoratorFactory());
      factories.put(DecoratorType.INSURANCE, new InsuranceDecoratorFactory());
      return this;
   }

   public SalaryPipelineBuilder withFactory(DecoratorType type, DecoratorFactory factory) {
      factories.put(type, factory);
      return this;
   }

   public SalaryEnhancementPipeline build() {
      if (baseSalary == null) throw new IllegalStateException("Base salary required");
      return new SalaryEnhancementPipeline(baseSalary, factories);
   }
}