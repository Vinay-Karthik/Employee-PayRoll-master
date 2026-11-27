package report;

import model.PaySlip;

import java.math.BigDecimal;
import java.util.List;

public class PayrollReportFormatter {
   private static final String HEADER_FORMAT = "%-5s %-15s %-12s %-14s %-14s %-14s %-14s\n";
   private static final String ROW_FORMAT = "%-5d %-15s %-12s Rs. %-11.2f Rs. %-11.2f Rs. %-11.2f Rs. %-11.2f\n";
   private static final String LINE = "=".repeat(90);
   private static final String SEPARATOR = "-".repeat(90);

   public String format(List<PaySlip> slips) {
      if (slips.isEmpty()) {
         return "No payroll data.";
      }

      BigDecimal totalGross = BigDecimal.ZERO;
      BigDecimal totalTax = BigDecimal.ZERO;
      BigDecimal totalDed = BigDecimal.ZERO;
      BigDecimal totalNet = BigDecimal.ZERO;

      StringBuilder sb = new StringBuilder();
      sb.append(LINE).append("\n");
      sb.append(String.format(HEADER_FORMAT, "ID", "Name", "Type", "Gross", "Tax", "Deduct", "Net"));
      sb.append(SEPARATOR).append("\n");

      for (PaySlip s : slips) {
         totalGross = totalGross.add(s.grossPay());
         totalTax = totalTax.add(s.taxWithholding());
         totalDed = totalDed.add(s.deductions());
         totalNet = totalNet.add(s.netPay());

         sb.append(String.format(ROW_FORMAT,
                 s.employeeId(), s.employeeName(), s.roleCategory(),
                 s.grossPay(), s.taxWithholding(), s.deductions(), s.netPay()));
      }

      sb.append(LINE).append("\n");
      sb.append(String.format("TOTALS: Gross Rs. %.2f | Tax Rs. %.2f | Deductions Rs. %.2f | Net Rs. %.2f\n",
              totalGross, totalTax, totalDed, totalNet));
      sb.append(LINE);

      return sb.toString();
   }
}