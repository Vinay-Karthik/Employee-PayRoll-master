package decorator.pipeline.factory;

import decorator.component.SalaryComponent;
import decorator.decorator.InsuranceDeductionDecorator;
import decorator.pipeline.DecoratorFactory;
import java.math.BigDecimal;

public class InsuranceDecoratorFactory implements DecoratorFactory {
    public SalaryComponent create(SalaryComponent wrapped, BigDecimal amount) {
        return new InsuranceDeductionDecorator(wrapped, amount);
    }
}
