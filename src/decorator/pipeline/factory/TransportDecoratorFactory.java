package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.TransportAllowanceDecorator;
import decorator.pipeline.DecoratorFactory;

import java.math.BigDecimal;

public class TransportDecoratorFactory implements DecoratorFactory {
   @Override
   public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
      if (amount == null) {
         return new TransportAllowanceDecorator(wrapped);
      }
      return new TransportAllowanceDecorator(wrapped, amount);
   }
}