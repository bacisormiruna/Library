package mapper;

import model.Book;
import model.Employee;
import model.builder.BookBuilder;
import model.builder.EmployeeBuilder;
import view.model.BookDTO;
import view.model.EmployeeDTO;
import view.model.builder.BookDTOBuilder;
import view.model.builder.EmployeeDTOBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        return new EmployeeDTOBuilder().setId(employee.getId()).setUsername(employee.getUsername()).setRoles(employee.getRoles()).build();
    }

    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        return new EmployeeBuilder().setId(employeeDTO.getId()).setUsername(employeeDTO.getUsername()).setRole(employeeDTO.getRole()).build();
    }

    public static List<EmployeeDTO> convertEmployeeListToEmployeeDTOList(List<Employee> employees){
        return employees.parallelStream().map(EmployeeMapper::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }

    public static List<Employee> convertEmployeeDTOToEmployeeList(List<EmployeeDTO>  employeeDTOS){
        return employeeDTOS.parallelStream().map(EmployeeMapper::convertEmployeeDTOToEmployee).collect(Collectors.toList());
    }
}
