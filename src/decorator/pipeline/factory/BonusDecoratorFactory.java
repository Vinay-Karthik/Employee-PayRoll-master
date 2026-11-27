package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.PerformanceBonusDecorator;
import decorator.pipeline.DecoratorFactory;
import java.math.BigDecimal;

public class BonusDecoratorFactory implements DecoratorFactory {
    public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
        return new PerformanceBonusDecorator(wrapped, amount);
    }
}
