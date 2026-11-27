package decorator.decorator;

import decorator.component.SalaryComponent;

import java.math.BigDecimal;

public class TransportAllowanceDecorator extends SalaryDecorator {
   private static final BigDecimal DEFAULT = new BigDecimal("1600");

   public TransportAllowanceDecorator(SalaryComponent wrapped) {
      this(wrapped, DEFAULT);
   }

   public TransportAllowanceDecorator(SalaryComponent wrapped, BigDecimal customAmount) {
      super(wrapped, customAmount);
   }

   @Override
   public BigDecimal calculateSalary() {
      return wrapped.calculateSalary().add(amount);
   }

   @Override
   public String getDescription() {
      return wrapped.getDescription() + " + Transport (" + amount + ")";
   }
}
