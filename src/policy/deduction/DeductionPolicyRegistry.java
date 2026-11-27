package policy.deduction;

import model.EmployeeType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeductionPolicyRegistry {
   private final Map<EmployeeType, Supplier<DeductionPolicy>> registry = new HashMap<>();

   public DeductionPolicyRegistry() {
      register(EmployeeType.FULL_TIME, FullTimeDeductionPolicy::new);
      register(EmployeeType.CONTRACTOR, ContractorDeductionPolicy::new);
      register(EmployeeType.FIXED_CONTRACTOR, ContractorDeductionPolicy::new);
   }

   public void register(EmployeeType type, Supplier<DeductionPolicy> supplier) {
      registry.put(type, supplier);
   }

   public DeductionPolicy get(EmployeeType type) {
      Supplier<DeductionPolicy> supplier = registry.get(type);
      if (supplier == null) throw new IllegalArgumentException("No deduction policy for: " + type);
      return supplier.get();
   }
}
