package service;

import model.Employee;
import model.PaySlip;
import repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

public class PayrollServiceImpl implements PayrollService {
   private final EmployeeRepository repo;

   public PayrollServiceImpl(EmployeeRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<Employee> getAllEmployees() {
      return repo.findAll();
   }

   @Override
   public Optional<Employee> getEmployeeById(int id) {
      return repo.findById(id);
   }

   @Override
   public void registerEmployee(Employee employee) {
      repo.save(employee);
   }

   @Override
   public boolean removeEmployee(int id) {
      return repo.deleteById(id);
   }

   @Override
   public Optional<PaySlip> processPaySlip(int id) {
      return repo.findById(id).map(Employee::generatePaySlip);
   }

   @Override
   public List<PaySlip> processAllPaySlips() {
      return repo.findAll().stream().map(Employee::generatePaySlip).toList();
   }
}
