package model;

import policy.deduction.DeductionPolicy;
import policy.deduction.DeductionPolicyRegistry;
import policy.tax.TaxPolicy;
import policy.tax.TaxPolicyRegistry;

import java.math.BigDecimal;

public class ContractorEmployee extends Employee {
   private final BigDecimal hourlyRate;
   private final BigDecimal hoursWorked;
   private final BigDecimal fixedContractAmount;

   private ContractorEmployee(Builder builder,
                              TaxPolicyRegistry taxRegistry, DeductionPolicyRegistry deductionRegistry) {
      super(builder.id, builder.name, builder.department, builder.bankAccount,
              EmployeeType.CONTRACTOR, taxRegistry, deductionRegistry);
      this.hourlyRate = builder.hourlyRate;
      this.hoursWorked = builder.hoursWorked;
      this.fixedContractAmount = builder.fixedContractAmount;
   }

   ContractorEmployee(Builder builder, TaxPolicy taxPolicy, DeductionPolicy deductionPolicy) {
      super(builder.id, builder.name, builder.department, builder.bankAccount, taxPolicy, deductionPolicy);
      this.hourlyRate = builder.hourlyRate;
      this.hoursWorked = builder.hoursWorked;
      this.fixedContractAmount = builder.fixedContractAmount;
   }

   public BigDecimal getHourlyRate() {
      return hourlyRate;
   }

   public BigDecimal getHoursWorked() {
      return hoursWorked;
   }

   public BigDecimal getFixedContractAmount() {
      return fixedContractAmount;
   }

   @Override
   public BigDecimal calculateGrossPay() {
      if (fixedContractAmount != null && fixedContractAmount.signum() > 0) {
         return fixedContractAmount;
      }
      BigDecimal rate = hourlyRate != null ? hourlyRate : BigDecimal.ZERO;
      BigDecimal hours = hoursWorked != null ? hoursWorked : BigDecimal.ZERO;
      return rate.multiply(hours);
   }

   @Override
   public EmployeeType getType() {
      return EmployeeType.CONTRACTOR;
   }

   public static class Builder {
      private int id;
      private String name;
      private String department;
      private String bankAccount;
      private BigDecimal hourlyRate;
      private BigDecimal hoursWorked;
      private BigDecimal fixedContractAmount;
      private TaxRegime taxRegime = TaxRegime.NEW;
      private TaxPolicy taxPolicy;
      private DeductionPolicy deductionPolicy;

      public Builder id(int id) {
         this.id = id;
         return this;
      }

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder department(String department) {
         this.department = department;
         return this;
      }

      public Builder bankAccount(String bankAccount) {
         this.bankAccount = bankAccount;
         return this;
      }

      public Builder hourlyRate(BigDecimal hourlyRate) {
         this.hourlyRate = hourlyRate;
         return this;
      }

      public Builder hoursWorked(BigDecimal hoursWorked) {
         this.hoursWorked = hoursWorked;
         return this;
      }

      public Builder fixedContractAmount(BigDecimal fixedContractAmount) {
         this.fixedContractAmount = fixedContractAmount;
         return this;
      }

      public Builder taxRegime(TaxRegime taxRegime) {
         this.taxRegime = taxRegime;
         return this;
      }

      public Builder taxPolicy(TaxPolicy taxPolicy) {
         this.taxPolicy = taxPolicy;
         return this;
      }

      public Builder deductionPolicy(DeductionPolicy deductionPolicy) {
         this.deductionPolicy = deductionPolicy;
         return this;
      }

      public ContractorEmployee build() {
         if (taxPolicy != null && deductionPolicy != null) {
            return new ContractorEmployee(this, taxPolicy, deductionPolicy);
         }
         return new ContractorEmployee(this, new TaxPolicyRegistry(), new DeductionPolicyRegistry());
      }
   }
}
