package service;

import model.Employee;
import model.PaySlip;

import java.util.List;
import java.util.Optional;

public interface PayrollService {
   List<Employee> getAllEmployees();

   Optional<Employee> getEmployeeById(int id);

   void registerEmployee(Employee employee);

   boolean removeEmployee(int id);

   Optional<PaySlip> processPaySlip(int id);

   List<PaySlip> processAllPaySlips();
}
