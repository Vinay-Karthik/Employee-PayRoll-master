package decorator.pipeline;

import decorator.component.BaseSalary;
import decorator.component.SalaryComponent;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class SalaryEnhancementPipeline {
    private final Set<DecoratorType> applied = EnumSet.noneOf(DecoratorType.class);
    private final Map<DecoratorType, BiFunction<SalaryComponent, BigDecimal, SalaryComponent>> creators;
    private SalaryComponent current;

    public SalaryEnhancementPipeline(BigDecimal baseSalary,
                                     Map<DecoratorType, BiFunction<SalaryComponent, BigDecimal, SalaryComponent>> creators) {
        this.current = new BaseSalary(baseSalary);
        this.creators = Map.copyOf(creators);
    }

    public SalaryEnhancementPipeline apply(DecoratorType type, BigDecimal customAmount) {
        if (applied.contains(type)) {
            throw new IllegalStateException(type + " already applied");
        }

        BiFunction<SalaryComponent, BigDecimal, SalaryComponent> creator = creators.get(type);
        if (creator == null) {
            throw new IllegalStateException("No creator registered for: " + type);
        }

        current = creator.apply(current, customAmount);

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