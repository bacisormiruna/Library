package service.employee;

import model.Book;
import model.Employee;

import java.util.List;

public interface EmployeeService {

    boolean add(Employee employee);
    boolean delete(Employee employee);
    List<Employee> findAll();
    void generateEmployeeSalesReport(String filePath);
}
