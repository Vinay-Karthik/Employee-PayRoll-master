package factory;

import java.math.BigDecimal;

public class ContractorData implements EmployeeData {
   private final int id;
   private final String name;
   private final String department;
   private final String bankAccount;
   private final BigDecimal hourlyRate;
   private final BigDecimal hoursWorked;

   public ContractorData(int id, String name, String department, String bankAccount,
                         BigDecimal hourlyRate, BigDecimal hoursWorked) {
      this.id = id;
      this.name = name;
      this.department = department;
      this.bankAccount = bankAccount;
      this.hourlyRate = hourlyRate;
      this.hoursWorked = hoursWorked;
   }

   public int id() {
      return id;
   }

   public String name() {
      return name;
   }

   public String department() {
      return department;
   }

   public String bankAccount() {
      return bankAccount;
   }

   public BigDecimal hourlyRate() {
      return hourlyRate;
   }

   public BigDecimal hoursWorked() {
      return hoursWorked;
   }
}