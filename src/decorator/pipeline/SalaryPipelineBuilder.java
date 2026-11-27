package decorator.pipeline;

import decorator.component.SalaryComponent;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

public class SalaryPipelineBuilder {
    private final Map<DecoratorType, BiFunction<SalaryComponent, BigDecimal, SalaryComponent>> creators = new EnumMap<>(DecoratorType.class);
    private BigDecimal baseSalary;

    public SalaryPipelineBuilder baseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
        return this;
    }

    public SalaryPipelineBuilder withDefaults() {
        for (DecoratorConfig config : DecoratorConfig.DEFAULT_DECORATORS) {
            creators.put(config.type(), config.decoratorCreator());
        }
        return this;
    }

    public SalaryPipelineBuilder withCreator(DecoratorType type, BiFunction<SalaryComponent, BigDecimal, SalaryComponent> creator) {
        creators.put(type, creator);
        return this;
    }

    public SalaryEnhancementPipeline build() {
        if (baseSalary == null) throw new IllegalStateException("Base salary required");
        return new SalaryEnhancementPipeline(baseSalary, creators);
    }
}