package policy.tax;

import model.enums.EmployeeType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TaxPolicyRegistry {
   private final Map<EmployeeType, Supplier<TaxPolicy>> registry = new HashMap<>();

   public TaxPolicyRegistry() {
      register(EmployeeType.FULL_TIME, FullTimeTaxPolicy::new);
      register(EmployeeType.CONTRACTOR, ContractorTaxPolicy::new);
      register(EmployeeType.FIXED_CONTRACTOR, ContractorTaxPolicy::new);
   }

   public void register(EmployeeType type, Supplier<TaxPolicy> supplier) {
      registry.put(type, supplier);
   }

   public TaxPolicy get(EmployeeType type) {
      Supplier<TaxPolicy> supplier = registry.get(type);
      if (supplier == null) throw new IllegalArgumentException("No tax policy for: " + type);
      return supplier.get();
   }
}
