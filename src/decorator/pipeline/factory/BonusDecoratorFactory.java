package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.PerformanceBonusDecorator;
import decorator.pipeline.DecoratorFactory;

import java.math.BigDecimal;

public class BonusDecoratorFactory implements DecoratorFactory {
   @Override
   public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
      if (amount == null) {
         return new PerformanceBonusDecorator(wrapped);
      }
      return new PerformanceBonusDecorator(wrapped, amount);
   }
}