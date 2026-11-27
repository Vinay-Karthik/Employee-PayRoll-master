package policy.deduction;

import java.math.BigDecimal;

public class ContractorDeductionPolicy implements DeductionPolicy {
    private static final BigDecimal SERVICE_FEE_RATE = new BigDecimal("0.02");

    @Override
    public BigDecimal calculate(BigDecimal gross) {
        return gross.multiply(SERVICE_FEE_RATE);
    }
}
