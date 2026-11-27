package decorator.pipeline;

import decorator.pipeline.factory.*;
import decorator.pipeline.defaultamount.*;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class SalaryPipelineBuilder {
    private BigDecimal baseSalary;
    private final Map<DecoratorType, DecoratorFactory> factories = new EnumMap<>(DecoratorType.class);
    private final Map<DecoratorType, DefaultAmountProvider> defaults = new EnumMap<>(DecoratorType.class);

    public SalaryPipelineBuilder baseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
        return this;
    }

    public SalaryPipelineBuilder withDefaults() {
        factories.put(DecoratorType.HRA, new HraDecoratorFactory());
        factories.put(DecoratorType.TRANSPORT, new TransportDecoratorFactory());
        factories.put(DecoratorType.BONUS, new BonusDecoratorFactory());
        factories.put(DecoratorType.INSURANCE, new InsuranceDecoratorFactory());

        defaults.put(DecoratorType.HRA, new HraDefaultAmount());
        defaults.put(DecoratorType.TRANSPORT, new TransportDefaultAmount());
        defaults.put(DecoratorType.BONUS, new BonusDefaultAmount());
        defaults.put(DecoratorType.INSURANCE, new InsuranceDefaultAmount());
        return this;
    }

    public SalaryPipelineBuilder withFactory(DecoratorType type, DecoratorFactory factory) {
        factories.put(type, factory);
        return this;
    }

    public SalaryPipelineBuilder withDefaultAmount(DecoratorType type, DefaultAmountProvider provider) {
        defaults.put(type, provider);
        return this;
    }

    public SalaryEnhancementPipeline build() {
        if (baseSalary == null) throw new IllegalStateException("Base salary required");
        return new SalaryEnhancementPipeline(baseSalary, factories, defaults);
    }
}
