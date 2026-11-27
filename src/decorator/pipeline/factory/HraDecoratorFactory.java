package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.HraDecorator;
import decorator.pipeline.DecoratorFactory;
import java.math.BigDecimal;

public class HraDecoratorFactory implements DecoratorFactory {
    public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
        return new HraDecorator(wrapped, amount);
    }
}
