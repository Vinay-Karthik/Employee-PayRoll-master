package decorator.decorator;

import decorator.component.SalaryComponent;

import java.math.BigDecimal;

public class HraDecorator extends SalaryDecorator {
   public HraDecorator(SalaryComponent wrapped) {
      this(wrapped, wrapped.baseSalary().multiply(new BigDecimal("0.20")));
   }

   public HraDecorator(SalaryComponent wrapped, BigDecimal customAmount) {
      super(wrapped, customAmount);
   }

   @Override
   public BigDecimal calculateSalary() {
      return wrapped.calculateSalary().add(amount);
   }

   @Override
   public String getDescription() {
      return wrapped.getDescription() + " + HRA (" + amount + ")";
   }
}
