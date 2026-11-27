package policy.deduction;

import java.math.BigDecimal;

public interface DeductionPolicy {
    BigDecimal calculate(BigDecimal gross);
}
