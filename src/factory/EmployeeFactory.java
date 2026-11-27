package factory;

import model.ContractorEmployee;
import model.Employee;
import model.EmployeeType;
import model.FullTimeEmployee;
import policy.deduction.DeductionPolicyRegistry;
import policy.tax.TaxPolicyRegistry;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EmployeeFactory {
    private final Map<EmployeeType, Function<EmployeeFactoryParams, Employee>> creators = new HashMap<>();
    private final TaxPolicyRegistry taxRegistry;
    private final DeductionPolicyRegistry deductionRegistry;

    public EmployeeFactory() {
        this(new TaxPolicyRegistry(), new DeductionPolicyRegistry());
    }

    public EmployeeFactory(TaxPolicyRegistry taxRegistry, DeductionPolicyRegistry deductionRegistry) {
        this.taxRegistry = taxRegistry;
        this.deductionRegistry = deductionRegistry;
        registerDefaults();
    }

    private void registerDefaults() {
        registerType(EmployeeType.FULL_TIME, p -> new FullTimeEmployee(
            p.id(), p.name(), p.department(), p.bankAccount(),
            p.baseSalary(), p.bonus(), taxRegistry, deductionRegistry
        ));
        registerType(EmployeeType.CONTRACTOR, p -> new ContractorEmployee.Builder()
            .id(p.id()).name(p.name()).department(p.department()).bankAccount(p.bankAccount())
            .hourlyRate(p.hourlyRate()).hoursWorked(p.hoursWorked()).build()
        );
    }

    public void registerType(EmployeeType type, Function<EmployeeFactoryParams, Employee> creator) {
        creators.put(type, creator);
    }

    public Employee create(EmployeeFactoryParams params) {
        Function<EmployeeFactoryParams, Employee> creator = creators.get(params.type());
        if (creator == null) {
            throw new IllegalArgumentException("Unknown employee type: " + params.type());
        }
        return creator.apply(params);
    }

    public Employee createFullTime(int id, String name, String department, String bankAccount,
                                    BigDecimal baseSalary, BigDecimal bonus) {
        return create(new EmployeeFactoryParams(EmployeeType.FULL_TIME, id, name, department, bankAccount,
            baseSalary, bonus, null, null));
    }

    public Employee createContractor(int id, String name, String department, String bankAccount,
                                      BigDecimal hourlyRate, BigDecimal hoursWorked) {
        return create(new EmployeeFactoryParams(EmployeeType.CONTRACTOR, id, name, department, bankAccount,
            null, null, hourlyRate, hoursWorked));
    }

    public Employee createContractorFixed(int id, String name, String department, String bankAccount,
                                           BigDecimal fixedContractAmount) {
        return create(new EmployeeFactoryParams(EmployeeType.CONTRACTOR, id, name, department, bankAccount,
            fixedContractAmount, null, null, null));
    }

    public record EmployeeFactoryParams(
        EmployeeType type,
        int id,
        String name,
        String department,
        String bankAccount,
        BigDecimal baseSalary,
        BigDecimal bonus,
        BigDecimal hourlyRate,
        BigDecimal hoursWorked
    ) {
        public EmployeeFactoryParams(EmployeeType type, int id, String name, String department, String bankAccount,
                                      BigDecimal baseSalary, BigDecimal bonus, BigDecimal hourlyRate, BigDecimal hoursWorked) {
            this.type = type; this.id = id; this.name = name; this.department = department;
            this.bankAccount = bankAccount; this.baseSalary = baseSalary; this.bonus = bonus;
            this.hourlyRate = hourlyRate; this.hoursWorked = hoursWorked;
        }
    }
}
