package decorator.pipeline;

import decorator.component.SalaryComponent;
import java.math.BigDecimal;

@FunctionalInterface
public interface DefaultAmountProvider {
    BigDecimal getDefault(SalaryComponent base);
}
