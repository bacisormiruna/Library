package model.builder;

import model.Book;
import model.Employee;
import model.Role;

import java.util.List;

public class EmployeeBuilder {
    private Employee employee;

    public EmployeeBuilder(){
        employee = new Employee();
    }

    public EmployeeBuilder setId(Long id){
        employee.setId(id);
        return this;
    }

    public EmployeeBuilder setUsername(String username){
        employee.setUsername(username);
        return this;
    }

    public EmployeeBuilder setRole(String role){
        employee.setRoles(role);
        return this;
    }

    public Employee build(){
        return employee;
    }
}
