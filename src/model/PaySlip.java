package model;

import model.enums.TaxRegime;

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
      String line = "================================================================";
      String separator = "----------------------------------------------------------------";

      String sb = line + "\n" +
              "                         PAY SLIP\n" +
              line + "\n" +
              String.format("  Employee ID   : %d\n", employeeId) +
              String.format("  Name          : %s\n", employeeName) +
              String.format("  Department    : %s\n", department) +
              String.format("  Role          : %s\n", roleCategory) +
              String.format("  Bank Account  : %s\n", bankAccountNumber) +
              String.format("  Tax Regime    : %s\n", taxRegime) +
              separator + "\n" +
              String.format("  Gross Pay     : Rs. %,.2f\n", grossPay) +
              String.format("  Tax Withheld  : Rs. %,.2f\n", taxWithholding) +
              String.format("  Deductions    : Rs. %,.2f\n", deductions) +
              separator + "\n" +
              String.format("  NET PAY       : Rs. %,.2f\n", netPay) +
              line;
      return sb;
   }
}