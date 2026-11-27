package decorator.decorator;

import decorator.component.SalaryComponent;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class SalaryDecorator implements SalaryComponent {
   protected final SalaryComponent wrapped;
   protected final BigDecimal amount;

   protected SalaryDecorator(SalaryComponent wrapped, BigDecimal amount) {
      this.wrapped = wrapped;
      this.amount = amount != null ? amount.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
   }

   @Override
   public BigDecimal baseSalary() {
      return wrapped.baseSalary();
   }

   @Override
   public String getDescription() {
      return wrapped.getDescription();
   }
}
