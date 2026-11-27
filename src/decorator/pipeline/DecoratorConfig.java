package decorator.pipeline;

import decorator.component.SalaryComponent;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public record DecoratorConfig(
    DecoratorType type,
    String displayName,
    Function<BigDecimal, BigDecimal> defaultAmountCalculator,
    BiFunction<SalaryComponent, BigDecimal, SalaryComponent> decoratorCreator
) {
    public static final List<DecoratorConfig> DEFAULT_DECORATORS = List.of(
        new DecoratorConfig(
            DecoratorType.HRA,
            "HRA",
            base -> base.multiply(new BigDecimal("0.20")),
            (wrapped, amount) -> amount == null
                ? new decorator.decorator.HraDecorator(wrapped)
                : new decorator.decorator.HraDecorator(wrapped, amount)
        ),
        new DecoratorConfig(
            DecoratorType.TRANSPORT,
            "Transport",
            base -> new BigDecimal("1600"),
            (wrapped, amount) -> amount == null
                ? new decorator.decorator.TransportAllowanceDecorator(wrapped)
                : new decorator.decorator.TransportAllowanceDecorator(wrapped, amount)
        ),
        new DecoratorConfig(
            DecoratorType.BONUS,
            "Bonus",
            base -> base.multiply(new BigDecimal("0.10")),
            (wrapped, amount) -> amount == null
                ? new decorator.decorator.PerformanceBonusDecorator(wrapped)
                : new decorator.decorator.PerformanceBonusDecorator(wrapped, amount)
        ),
        new DecoratorConfig(
            DecoratorType.INSURANCE,
            "Insurance",
            base -> new BigDecimal("150"),
            (wrapped, amount) -> amount == null
                ? new decorator.decorator.InsuranceDeductionDecorator(wrapped)
                : new decorator.decorator.InsuranceDeductionDecorator(wrapped, amount)
        )
    );
}