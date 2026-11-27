package decorator.pipeline;

import decorator.component.BaseSalary;
import decorator.component.SalaryComponent;
import decorator.decorator.HraDecorator;
import decorator.decorator.InsuranceDeductionDecorator;
import decorator.decorator.PerformanceBonusDecorator;
import decorator.decorator.TransportAllowanceDecorator;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

public class SalaryEnhancementPipeline {
    private SalaryComponent current;
    private final Map<DecoratorType, Boolean> applied = new EnumMap<>(DecoratorType.class);
    private final Map<DecoratorType, BiFunction<SalaryComponent, BigDecimal, SalaryComponent>> decoratorRegistry;

    public SalaryEnhancementPipeline(BigDecimal baseSalary) {
        this.current = new BaseSalary(baseSalary);
        this.decoratorRegistry = createDefaultRegistry();
    }

    private Map<DecoratorType, BiFunction<SalaryComponent, BigDecimal, SalaryComponent>> createDefaultRegistry() {
        Map<DecoratorType, BiFunction<SalaryComponent, BigDecimal, SalaryComponent>> map = new EnumMap<>(DecoratorType.class);
        map.put(DecoratorType.HRA, (c, a) -> new HraDecorator(c, a));
        map.put(DecoratorType.TRANSPORT, (c, a) -> new TransportAllowanceDecorator(c, a));
        map.put(DecoratorType.BONUS, (c, a) -> new PerformanceBonusDecorator(c, a));
        map.put(DecoratorType.INSURANCE, (c, a) -> new InsuranceDeductionDecorator(c, a));
        return map;
    }

    public void registerDecorator(DecoratorType type, BiFunction<SalaryComponent, BigDecimal, SalaryComponent> factory) {
        decoratorRegistry.put(type, factory);
    }

    public SalaryEnhancementPipeline applyHRA() { return apply(DecoratorType.HRA, null); }
    public SalaryEnhancementPipeline applyHRA(BigDecimal custom) { return apply(DecoratorType.HRA, custom); }

    public SalaryEnhancementPipeline applyTransport() { return apply(DecoratorType.TRANSPORT, null); }
    public SalaryEnhancementPipeline applyTransport(BigDecimal custom) { return apply(DecoratorType.TRANSPORT, custom); }

    public SalaryEnhancementPipeline applyBonus() { return apply(DecoratorType.BONUS, null); }
    public SalaryEnhancementPipeline applyBonus(BigDecimal custom) { return apply(DecoratorType.BONUS, custom); }

    public SalaryEnhancementPipeline applyInsurance() { return apply(DecoratorType.INSURANCE, null); }
    public SalaryEnhancementPipeline applyInsurance(BigDecimal custom) { return apply(DecoratorType.INSURANCE, custom); }

    private SalaryEnhancementPipeline apply(DecoratorType type, BigDecimal custom) {
        if (Boolean.TRUE.equals(applied.get(type)))
            throw new IllegalStateException(type + " already applied");

        BiFunction<SalaryComponent, BigDecimal, SalaryComponent> factory = decoratorRegistry.get(type);
        if (factory == null)
            throw new IllegalArgumentException("Unknown decorator type: " + type);

        BigDecimal amount = custom != null ? custom : defaultAmount(type);
        current = factory.apply(current, amount);
        applied.put(type, true);
        return this;
    }

    private BigDecimal defaultAmount(DecoratorType type) {
        return switch (type) {
            case HRA -> current.getBaseSalary().multiply(new BigDecimal("0.20"));
            case TRANSPORT -> new BigDecimal("1600");
            case BONUS -> current.getBaseSalary().multiply(new BigDecimal("0.10"));
            case INSURANCE -> new BigDecimal("150");
        };
    }

    public BigDecimal getCurrentSalary() { return current.calculateSalary(); }
    public BigDecimal getBaseSalary() { return current.getBaseSalary(); }
    public String getDescription() { return current.getDescription(); }
    public boolean isApplied(DecoratorType type) { return Boolean.TRUE.equals(applied.get(type)); }
}
