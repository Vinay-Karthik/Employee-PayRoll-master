package policy.tax;

import model.enums.TaxRegime;

import java.math.BigDecimal;

public class FullTimeTaxPolicy implements TaxPolicy {
   private static final BigDecimal RATE_NEW = new BigDecimal("0.22");
   private static final BigDecimal RATE_OLD = new BigDecimal("0.20");
   private static final BigDecimal SURTAX_NEW = new BigDecimal("0.05");
   private static final BigDecimal SURTAX_OLD = new BigDecimal("0.04");
   private static final BigDecimal THRESHOLD_NEW = new BigDecimal("7000");
   private static final BigDecimal THRESHOLD_OLD = new BigDecimal("5000");

   @Override
   public BigDecimal calculate(BigDecimal gross, TaxRegime regime) {
      if (regime == TaxRegime.OLD) {
         BigDecimal tax = gross.multiply(RATE_OLD);
         if (gross.compareTo(THRESHOLD_OLD) > 0)
            tax = tax.add(gross.subtract(THRESHOLD_OLD).multiply(SURTAX_OLD));
         return tax;
      }
      BigDecimal tax = gross.multiply(RATE_NEW);
      if (gross.compareTo(THRESHOLD_NEW) > 0)
         tax = tax.add(gross.subtract(THRESHOLD_NEW).multiply(SURTAX_NEW));
      return tax;
   }
}
