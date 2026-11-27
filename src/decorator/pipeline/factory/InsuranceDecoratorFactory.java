package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.InsuranceDeductionDecorator;
import decorator.pipeline.DecoratorFactory;

import java.math.BigDecimal;

public class InsuranceDecoratorFactory implements DecoratorFactory {
   @Override
   public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
      if (amount == null) {
         return new InsuranceDeductionDecorator(wrapped);
      }
      return new InsuranceDeductionDecorator(wrapped, amount);
   }
}