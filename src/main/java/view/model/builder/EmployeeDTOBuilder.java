package view.model.builder;

import model.Role;
import view.model.BookDTO;
import view.model.EmployeeDTO;

import java.util.List;

public class EmployeeDTOBuilder {
    private EmployeeDTO employeeDTO;
    public EmployeeDTOBuilder(){
        employeeDTO = new EmployeeDTO();
    }
    public EmployeeDTOBuilder setId(Long id){
        employeeDTO.setId(id);
        return this;
    }
    public EmployeeDTOBuilder setUsername(String username){
        employeeDTO.setUsername(username);
        return this;
    }

    public EmployeeDTOBuilder setRoles(String roles){
        employeeDTO.setRoles(roles);
        return this;
    }

    public EmployeeDTO build(){
        return employeeDTO;
    }
}
