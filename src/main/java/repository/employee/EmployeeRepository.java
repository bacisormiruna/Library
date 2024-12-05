package repository.employee;

import model.Employee;

import java.util.List;

public interface EmployeeRepository {
    public List<Employee> findAll();
    public List<Employee> findEmployees();
    boolean add(Employee employee);
    boolean delete(Employee employee);
    int getNumberOfBooksSoldPerEmployee(Long employeeId);
    double getTotalSumOfBooks(Long employeeId);
}
