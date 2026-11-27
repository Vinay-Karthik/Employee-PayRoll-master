package repository;

import model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In-memory employee repository implementation.
 * SRP: Only in-memory storage concerns.
 * LSP: Can substitute any EmployeeRepository implementation.
 */
public class InMemoryEmployeeRepository implements EmployeeRepository {
   private final Map<Integer, Employee> employees = new ConcurrentHashMap<>();

   @Override
   public List<Employee> findAll() {
      return new ArrayList<>(employees.values());
   }

   @Override
   public Optional<Employee> findById(int id) {
      return Optional.ofNullable(employees.get(id));
   }

   @Override
   public void save(Employee employee) {
      if (employee.getId() <= 0) {
         throw new IllegalArgumentException("Employee ID must be positive");
      }
      employees.put(employee.getId(), employee);
   }

   @Override
   public boolean deleteById(int id) {
      return employees.remove(id) != null;
   }
}
