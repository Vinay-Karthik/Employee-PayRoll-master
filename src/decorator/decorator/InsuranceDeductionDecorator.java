package decorator.decorator;

import decorator.component.SalaryComponent;
import java.math.BigDecimal;

public class InsuranceDeductionDecorator extends SalaryDecorator {
    private static final BigDecimal DEFAULT = new BigDecimal("150");
    public InsuranceDeductionDecorator(SalaryComponent wrapped) { this(wrapped, DEFAULT); }
    public InsuranceDeductionDecorator(SalaryComponent wrapped, BigDecimal customAmount) { super(wrapped, customAmount); }

    @Override public BigDecimal calculateSalary() {
        BigDecimal current = wrapped.calculateSalary();
        if (amount.compareTo(current) > 0)
            throw new IllegalStateException("Insurance deduction (" + amount + ") exceeds salary (" + current + ")");
        return current.subtract(amount);
    }
    @Override public String getDescription() { return wrapped.getDescription() + " - Insurance (" + amount + ")"; }
}
