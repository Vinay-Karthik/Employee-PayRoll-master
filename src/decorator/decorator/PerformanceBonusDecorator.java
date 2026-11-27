package decorator.decorator;

import decorator.component.SalaryComponent;

import java.math.BigDecimal;

public class PerformanceBonusDecorator extends SalaryDecorator {
   public PerformanceBonusDecorator(SalaryComponent wrapped) {
      this(wrapped, wrapped.getBaseSalary().multiply(new BigDecimal("0.10")));
   }

   public PerformanceBonusDecorator(SalaryComponent wrapped, BigDecimal customAmount) {
      super(wrapped, customAmount);
   }

   @Override
   public BigDecimal calculateSalary() {
      return wrapped.calculateSalary().add(amount);
   }

   @Override
   public String getDescription() {
      return wrapped.getDescription() + " + Bonus (" + amount + ")";
   }
}
