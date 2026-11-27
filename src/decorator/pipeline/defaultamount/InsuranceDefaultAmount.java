package decorator.pipeline.defaultamount;

import decorator.component.SalaryComponent;
import decorator.pipeline.DefaultAmountProvider;
import java.math.BigDecimal;

public class InsuranceDefaultAmount implements DefaultAmountProvider {
    public BigDecimal getDefault(SalaryComponent base) {
        return new BigDecimal("150");
    }
}
