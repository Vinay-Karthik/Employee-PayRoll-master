package model;

import model.enums.EmployeeType;
import model.enums.TaxRegime;
import policy.deduction.DeductionPolicy;
import policy.deduction.DeductionPolicyRegistry;
import policy.tax.TaxPolicy;
import policy.tax.TaxPolicyRegistry;

import java.math.BigDecimal;

public class FullTimeEmployee extends Employee {
   private BigDecimal baseSalary;
   private BigDecimal bonus;

   private FullTimeEmployee(Builder builder,
                            TaxPolicyRegistry taxRegistry, DeductionPolicyRegistry deductionRegistry) {
      super(builder.id, builder.name, builder.department, builder.bankAccount,
              EmployeeType.FULL_TIME, taxRegistry, deductionRegistry);
      this.baseSalary = builder.baseSalary != null ? builder.baseSalary : BigDecimal.ZERO;
      this.bonus = builder.bonus != null ? builder.bonus : BigDecimal.ZERO;
   }

   FullTimeEmployee(Builder builder, TaxPolicy taxPolicy, DeductionPolicy deductionPolicy) {
      super(builder.id, builder.name, builder.department, builder.bankAccount, taxPolicy, deductionPolicy);
      this.baseSalary = builder.baseSalary != null ? builder.baseSalary : BigDecimal.ZERO;
      this.bonus = builder.bonus != null ? builder.bonus : BigDecimal.ZERO;
   }

   public BigDecimal getBaseSalary() {
      return baseSalary;
   }

   public void setBaseSalary(BigDecimal baseSalary) {
      this.baseSalary = baseSalary;
   }

   public BigDecimal getBonus() {
      return bonus;
   }

   public void setBonus(BigDecimal bonus) {
      this.bonus = bonus;
   }

   @Override
   public BigDecimal calculateGrossPay() {
      return baseSalary.add(bonus);
   }

   @Override
   public EmployeeType getType() {
      return EmployeeType.FULL_TIME;
   }

   public static class Builder {
      private int id;
      private String name;
      private String department;
      private String bankAccount;
      private BigDecimal baseSalary;
      private BigDecimal bonus;
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

      public Builder baseSalary(BigDecimal baseSalary) {
         this.baseSalary = baseSalary;
         return this;
      }

      public Builder bonus(BigDecimal bonus) {
         this.bonus = bonus;
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

      public FullTimeEmployee build() {
         if (taxPolicy != null && deductionPolicy != null) {
            return new FullTimeEmployee(this, taxPolicy, deductionPolicy);
         }
         return new FullTimeEmployee(this, new TaxPolicyRegistry(), new DeductionPolicyRegistry());
      }
   }
}