package factory;

import model.ContractorEmployee;
import model.Employee;
import model.EmployeeType;
import model.FullTimeEmployee;
import policy.deduction.DeductionPolicyRegistry;
import policy.tax.TaxPolicyRegistry;

public class EmployeeFactory {
   private final TaxPolicyRegistry taxRegistry;
   private final DeductionPolicyRegistry deductionRegistry;

   public EmployeeFactory() {
      this(new TaxPolicyRegistry(), new DeductionPolicyRegistry());
   }

   public EmployeeFactory(TaxPolicyRegistry taxRegistry, DeductionPolicyRegistry deductionRegistry) {
      this.taxRegistry = taxRegistry;
      this.deductionRegistry = deductionRegistry;
   }

   public Employee create(EmployeeType type, EmployeeData data) {
      return switch (type) {
         case FULL_TIME -> createFullTime((FullTimeData) data);
         case CONTRACTOR -> createHourlyContractor((ContractorData) data);
         case FIXED_CONTRACTOR -> createFixedContractor((FixedContractorData) data);
         default -> throw new IllegalArgumentException("Unknown employee type: " + type);
      };
   }

   private Employee createFullTime(FullTimeData p) {
      return new FullTimeEmployee(
              p.id(), p.name(), p.department(), p.bankAccount(),
              p.baseSalary(), p.bonus(), taxRegistry, deductionRegistry
      );
   }

   private Employee createHourlyContractor(ContractorData p) {
      return new ContractorEmployee.Builder()
              .id(p.id()).name(p.name()).department(p.department()).bankAccount(p.bankAccount())
              .hourlyRate(p.hourlyRate()).hoursWorked(p.hoursWorked()).build();
   }

   private Employee createFixedContractor(FixedContractorData p) {
      return new ContractorEmployee.Builder()
              .id(p.id()).name(p.name()).department(p.department()).bankAccount(p.bankAccount())
              .fixedContractAmount(p.fixedContractAmount()).build();
   }
}