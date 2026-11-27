package decorator.pipeline;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public record DecoratorConfig(
    DecoratorType type,
    String displayName,
    Function<BigDecimal, BigDecimal> defaultAmountCalculator
) {
    public static final List<DecoratorConfig> DEFAULT_DECORATORS = List.of(
        new DecoratorConfig(DecoratorType.HRA, "HRA", base -> base.multiply(new BigDecimal("0.20"))),
        new DecoratorConfig(DecoratorType.TRANSPORT, "Transport", base -> new BigDecimal("1600")),
        new DecoratorConfig(DecoratorType.BONUS, "Bonus", base -> base.multiply(new BigDecimal("0.10"))),
        new DecoratorConfig(DecoratorType.INSURANCE, "Insurance", base -> new BigDecimal("150"))
    );
}