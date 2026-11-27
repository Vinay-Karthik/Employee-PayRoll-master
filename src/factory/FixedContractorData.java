package factory;

import java.math.BigDecimal;

public class FixedContractorData implements EmployeeData {
   private final int id;
   private final String name;
   private final String department;
   private final String bankAccount;
   private final BigDecimal fixedContractAmount;

   public FixedContractorData(int id, String name, String department, String bankAccount,
                              BigDecimal fixedContractAmount) {
      this.id = id;
      this.name = name;
      this.department = department;
      this.bankAccount = bankAccount;
      this.fixedContractAmount = fixedContractAmount;
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

   public BigDecimal fixedContractAmount() {
      return fixedContractAmount;
   }
}