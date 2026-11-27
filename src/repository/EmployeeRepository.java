package repository;

import model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Employee Repository - data access abstraction.
 * ISP: Only essential CRUD operations.
 * DIP: Abstraction for storage.
 */
public interface EmployeeRepository {
   List<Employee> findAll();

   Optional<Employee> findById(int id);

   void save(Employee employee);

   boolean deleteById(int id);
}
