package model;

import policy.deduction.DeductionPolicy;
import policy.deduction.DeductionPolicyRegistry;
import policy.tax.TaxPolicy;
import policy.tax.TaxPolicyRegistry;

import java.math.BigDecimal;

public class FullTimeEmployee extends Employee {
   private BigDecimal baseSalary;
   private BigDecimal bonus;

   public FullTimeEmployee(int id, String name, String department, String bankAccount,
                           BigDecimal baseSalary, BigDecimal bonus,
                           TaxPolicyRegistry taxRegistry, DeductionPolicyRegistry deductionRegistry) {
      super(id, name, department, bankAccount, EmployeeType.FULL_TIME, taxRegistry, deductionRegistry);
      this.baseSalary = baseSalary != null ? baseSalary : BigDecimal.ZERO;
      this.bonus = bonus != null ? bonus : BigDecimal.ZERO;
   }

   public FullTimeEmployee(int id, String name, String department, String bankAccount,
                           BigDecimal baseSalary, BigDecimal bonus) {
      this(id, name, department, bankAccount, baseSalary, bonus,
              new TaxPolicyRegistry(), new DeductionPolicyRegistry());
   }

   FullTimeEmployee(int id, String name, String department, String bankAccount,
                    BigDecimal baseSalary, BigDecimal bonus,
                    TaxPolicy taxPolicy, DeductionPolicy deductionPolicy) {
      super(id, name, department, bankAccount, taxPolicy, deductionPolicy);
      this.baseSalary = baseSalary != null ? baseSalary : BigDecimal.ZERO;
      this.bonus = bonus != null ? bonus : BigDecimal.ZERO;
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
}
