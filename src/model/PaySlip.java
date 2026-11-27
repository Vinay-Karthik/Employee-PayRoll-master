package model;

import java.math.BigDecimal;

public record PaySlip(
        int employeeId,
        String employeeName,
        String department,
        String roleCategory,
        String bankAccountNumber,
        BigDecimal grossPay,
        BigDecimal taxWithholding,
        BigDecimal deductions,
        BigDecimal netPay,
        TaxRegime taxRegime
) {
   public PaySlip {
      if (employeeName == null || employeeName.isBlank())
         throw new IllegalArgumentException("Employee name cannot be blank");
   }
}
