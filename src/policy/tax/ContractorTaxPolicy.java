package policy.tax;

import model.enums.TaxRegime;

import java.math.BigDecimal;

public class ContractorTaxPolicy implements TaxPolicy {
   @Override
   public BigDecimal calculate(BigDecimal gross, TaxRegime regime) {
      return BigDecimal.ZERO;
   }
}
