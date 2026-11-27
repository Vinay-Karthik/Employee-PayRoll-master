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

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      String line = "================================================================";
      String separator = "----------------------------------------------------------------";

      sb.append(line).append("\n");
      sb.append("                         PAY SLIP\n");
      sb.append(line).append("\n");
      sb.append(String.format("  Employee ID   : %d\n", employeeId));
      sb.append(String.format("  Name          : %s\n", employeeName));
      sb.append(String.format("  Department    : %s\n", department));
      sb.append(String.format("  Role          : %s\n", roleCategory));
      sb.append(String.format("  Bank Account  : %s\n", bankAccountNumber));
      sb.append(String.format("  Tax Regime    : %s\n", taxRegime));
      sb.append(separator).append("\n");
      sb.append(String.format("  Gross Pay     : Rs. %,.2f\n", grossPay));
      sb.append(String.format("  Tax Withheld  : Rs. %,.2f\n", taxWithholding));
      sb.append(String.format("  Deductions    : Rs. %,.2f\n", deductions));
      sb.append(separator).append("\n");
      sb.append(String.format("  NET PAY       : Rs. %,.2f\n", netPay));
      sb.append(line);
      return sb.toString();
   }
}