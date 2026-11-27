package decorator.pipeline;
import decorator.component.BaseSalary;
import decorator.component.SalaryComponent;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class SalaryEnhancementPipeline {
    private SalaryComponent current;
    private final Set<DecoratorType> applied = EnumSet.noneOf(DecoratorType.class);
    
    private final Map<DecoratorType, DecoratorFactory> factories;
    private final Map<DecoratorType, DefaultAmountProvider> defaults;

    public SalaryEnhancementPipeline(BigDecimal baseSalary,
                                      Map<DecoratorType, DecoratorFactory> factories,
                                      Map<DecoratorType, DefaultAmountProvider> defaults) {
        this.current = new BaseSalary(baseSalary);
        this.factories = Map.copyOf(factories);
        this.defaults = Map.copyOf(defaults);
    }

    public SalaryEnhancementPipeline apply(DecoratorType type, BigDecimal customAmount) {
        if (applied.contains(type)) {
            throw new IllegalStateException(type + " already applied");
        }
        
        BigDecimal amount = customAmount != null 
            ? customAmount 
            : defaults.get(type).getDefault(current);
        
        current = factories.get(type).create(current, amount);
        applied.add(type);
        return this;
    }

    public SalaryEnhancementPipeline applyHRA() { return apply(DecoratorType.HRA, null); }
    public SalaryEnhancementPipeline applyHRA(BigDecimal a) { return apply(DecoratorType.HRA, a); }
    public SalaryEnhancementPipeline applyTransport() { return apply(DecoratorType.TRANSPORT, null); }
    public SalaryEnhancementPipeline applyTransport(BigDecimal a) { return apply(DecoratorType.TRANSPORT, a); }
    public SalaryEnhancementPipeline applyBonus() { return apply(DecoratorType.BONUS, null); }
    public SalaryEnhancementPipeline applyBonus(BigDecimal a) { return apply(DecoratorType.BONUS, a); }
    public SalaryEnhancementPipeline applyInsurance() { return apply(DecoratorType.INSURANCE, null); }
    public SalaryEnhancementPipeline applyInsurance(BigDecimal a) { return apply(DecoratorType.INSURANCE, a); }

    public BigDecimal getCurrentSalary() { return current.calculateSalary(); }
    public BigDecimal getBaseSalary() { return current.getBaseSalary(); }
    public String getDescription() { return current.getDescription(); }
    public boolean isApplied(DecoratorType type) { return applied.contains(type); }
}
