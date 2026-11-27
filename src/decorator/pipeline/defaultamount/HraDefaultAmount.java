package decorator.pipeline.defaultamount;

import decorator.component.SalaryComponent;
import decorator.pipeline.DefaultAmountProvider;
import java.math.BigDecimal;

public class HraDefaultAmount implements DefaultAmountProvider {
    public BigDecimal getDefault(SalaryComponent base) {
        return base.getBaseSalary().multiply(new BigDecimal("0.20"));
    }
}
