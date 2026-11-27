package service;

import model.ContractorEmployee;
import model.Employee;
import model.FullTimeEmployee;
import model.enums.TaxRegime;

import java.math.BigDecimal;

public class EmployeeFactory {


   public Employee createFullTime(int id, String name, String department, String bankAccount,
                                  BigDecimal baseSalary, BigDecimal bonus, TaxRegime taxRegime) {
      return new FullTimeEmployee.Builder()
              .id(id)
              .name(name)
              .department(department)
              .bankAccount(bankAccount)
              .baseSalary(baseSalary)
              .bonus(bonus)
              .taxRegime(taxRegime)
              .build();
   }

   public Employee createHourlyContractor(int id, String name, String department, String bankAccount,
                                          BigDecimal hourlyRate, BigDecimal hoursWorked, TaxRegime taxRegime) {
      return new ContractorEmployee.Builder()
              .id(id)
              .name(name)
              .department(department)
              .bankAccount(bankAccount)
              .hourlyRate(hourlyRate)
              .hoursWorked(hoursWorked)
              .taxRegime(taxRegime)
              .build();
   }

   public Employee createFixedContractor(int id, String name, String department, String bankAccount,
                                         BigDecimal fixedContractAmount, TaxRegime taxRegime) {
      return new ContractorEmployee.Builder()
              .id(id)
              .name(name)
              .department(department)
              .bankAccount(bankAccount)
              .fixedContractAmount(fixedContractAmount)
              .taxRegime(taxRegime)
              .build();
   }


   public Employee createFullTimeForSeed(int id, String name, String department, String bankAccount,
                                         BigDecimal baseSalary, BigDecimal bonus) {
      return new FullTimeEmployee.Builder()
              .id(id)
              .name(name)
              .department(department)
              .bankAccount(bankAccount)
              .baseSalary(baseSalary)
              .bonus(bonus)
              .taxRegime(TaxRegime.NEW)
              .build();
   }

   public Employee createHourlyContractorForSeed(int id, String name, String department, String bankAccount,
                                                 BigDecimal hourlyRate, BigDecimal hoursWorked) {
      return new ContractorEmployee.Builder()
              .id(id)
              .name(name)
              .department(department)
              .bankAccount(bankAccount)
              .hourlyRate(hourlyRate)
              .hoursWorked(hoursWorked)
              .taxRegime(TaxRegime.NEW)
              .build();
   }

   public Employee createFixedContractorForSeed(int id, String name, String department, String bankAccount,
                                                BigDecimal fixedContractAmount) {
      return new ContractorEmployee.Builder()
              .id(id)
              .name(name)
              .department(department)
              .bankAccount(bankAccount)
              .fixedContractAmount(fixedContractAmount)
              .taxRegime(TaxRegime.NEW)
              .build();
   }
}