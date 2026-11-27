package policy.tax;

import model.TaxRegime;

import java.math.BigDecimal;

public interface TaxPolicy {
   BigDecimal calculate(BigDecimal gross, TaxRegime regime);
}
