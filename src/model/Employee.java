package model;

import policy.deduction.DeductionPolicy;
import policy.deduction.DeductionPolicyRegistry;
import policy.tax.TaxPolicy;
import policy.tax.TaxPolicyRegistry;

import java.math.BigDecimal;

public abstract class Employee {
   protected final int id;
   private final TaxPolicy taxPolicy;
   private final DeductionPolicy deductionPolicy;
   protected String name;
   protected String department;
   protected String bankAccount;
   protected TaxRegime taxRegime = TaxRegime.NEW;

   protected Employee(int id, String name, String department, String bankAccount,
                      TaxPolicy taxPolicy, DeductionPolicy deductionPolicy) {
      this.id = id;
      this.name = name;
      this.department = department;
      this.bankAccount = bankAccount;
      this.taxPolicy = taxPolicy;
      this.deductionPolicy = deductionPolicy;
   }

   Employee(int id, String name, String department, String bankAccount,
            EmployeeType type,
            TaxPolicyRegistry taxRegistry, DeductionPolicyRegistry deductionRegistry) {
      this(id, name, department, bankAccount,
              taxRegistry.get(type), deductionRegistry.get(type));
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDepartment() {
      return department;
   }

   public void setDepartment(String department) {
      this.department = department;
   }

   public String getBankAccount() {
      return bankAccount;
   }

   public void setBankAccount(String bankAccount) {
      this.bankAccount = bankAccount;
   }

   public TaxRegime getTaxRegime() {
      return taxRegime;
   }

   public void setTaxRegime(TaxRegime taxRegime) {
      this.taxRegime = taxRegime;
   }

   public abstract BigDecimal calculateGrossPay();

   public abstract EmployeeType getType();

   public final PaySlip generatePaySlip() {
      BigDecimal gross = calculateGrossPay();
      BigDecimal tax = taxPolicy.calculate(gross, taxRegime);
      BigDecimal deductions = deductionPolicy.calculate(gross);
      BigDecimal net = gross.subtract(tax).subtract(deductions);
      return new PaySlip(id, name, department, getType().label, bankAccount, gross, tax, deductions, net, taxRegime);
   }
}
