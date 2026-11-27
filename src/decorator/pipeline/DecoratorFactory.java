package decorator.pipeline;

import decorator.component.SalaryComponent;
import java.math.BigDecimal;

@FunctionalInterface
public interface DecoratorFactory {
    SalaryComponent create(SalaryComponent wrapped, BigDecimal amount);
}
