package decorator.component;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseSalary implements SalaryComponent {
   private final BigDecimal baseSalary;

   public BaseSalary(BigDecimal baseSalary) {
      if (baseSalary == null || baseSalary.signum() < 0)
         throw new IllegalArgumentException("Base salary must be non-negative");
      this.baseSalary = baseSalary.setScale(2, RoundingMode.HALF_UP);
   }

   @Override
   public BigDecimal calculateSalary() {
      return baseSalary;
   }

   @Override
   public BigDecimal getBaseSalary() {
      return baseSalary;
   }

   @Override
   public String getDescription() {
      return "Base Salary (" + baseSalary + ")";
   }
}
