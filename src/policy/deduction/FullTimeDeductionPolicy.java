package policy.deduction;

import java.math.BigDecimal;

public class FullTimeDeductionPolicy implements DeductionPolicy {
   private static final BigDecimal HEALTH = new BigDecimal("150");
   private static final BigDecimal RETIREMENT_RATE = new BigDecimal("0.05");

   @Override
   public BigDecimal calculate(BigDecimal gross) {
      return HEALTH.add(gross.multiply(RETIREMENT_RATE));
   }
}
