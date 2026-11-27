package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.TransportAllowanceDecorator;
import decorator.pipeline.DecoratorFactory;
import java.math.BigDecimal;

public class TransportDecoratorFactory implements DecoratorFactory {
    public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
        return new TransportAllowanceDecorator(wrapped, amount);
    }
}
