package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.HraDecorator;
import decorator.pipeline.DecoratorFactory;

import java.math.BigDecimal;

public class HraDecoratorFactory implements DecoratorFactory {
   @Override
   public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
      if (amount == null) {
         return new HraDecorator(wrapped);
      }
      return new HraDecorator(wrapped, amount);
   }
}