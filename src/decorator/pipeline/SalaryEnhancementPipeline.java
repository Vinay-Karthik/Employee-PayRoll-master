package decorator.pipeline;

import decorator.component.BaseSalary;
import decorator.component.SalaryComponent;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class SalaryEnhancementPipeline {
    private final Set<DecoratorType> applied = EnumSet.noneOf(DecoratorType.class);
    private final Map<DecoratorType, DecoratorFactory> factories;
    private SalaryComponent current;

    public SalaryEnhancementPipeline(BigDecimal baseSalary,
                                     Map<DecoratorType, DecoratorFactory> factories) {
        this.current = new BaseSalary(baseSalary);
        this.factories = Map.copyOf(factories);
    }

    public SalaryEnhancementPipeline apply(DecoratorType type, BigDecimal customAmount) {
        if (applied.contains(type)) {
            throw new IllegalStateException(type + " already applied");
        }

        DecoratorFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalStateException("No factory registered for: " + type);
        }

        current = (customAmount != null)
                ? factory.create(current, customAmount)
                : factory.createWithDefault(current);

        applied.add(type);
        return this;
    }

    public BigDecimal getCurrentSalary() {
        return current.calculateSalary();
    }

    public BigDecimal getBaseSalary() {
        return current.getBaseSalary();
    }

    public String getDescription() {
        return current.getDescription();
    }

    public boolean isApplied(DecoratorType type) {
        return applied.contains(type);
    }
}