package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.EmployeeMapper;
import service.employee.EmployeeService; // Adăugăm serviciul de generare PDF
import view.AdminView;
import view.model.EmployeeDTO;
import view.model.builder.EmployeeDTOBuilder;

public class AdminController {
    private final AdminView adminView;
    private final EmployeeService employeeService;

    public AdminController(AdminView adminView, EmployeeService employeeService) {
        this.adminView = adminView;
        this.employeeService = employeeService;

        this.adminView.addAddButtonListener(new AddButtonListener());
        this.adminView.addDeleteButtonListener(new DeleteButtonListener());
        this.adminView.addGeneratePdfButtonListener(new GeneratePdfButtonListener());  // Adăugăm listener pentru PDF
    }

    private class AddButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = adminView.getEmployeeName();
            String roles = adminView.getEmployeeRole();

            if (username.isEmpty()) {
                adminView.addDisplayAlertMessage("Add Error", "Problem at adding a user", "Cannot have an empty field");
            } else {
                EmployeeDTO employeeDTO = new EmployeeDTOBuilder().setUsername(username).setRoles(roles).build();
                boolean addedEmployee = employeeService.add(EmployeeMapper.convertEmployeeDTOToEmployee(employeeDTO));
                if (addedEmployee) {
                    adminView.addDisplayAlertMessage("Successful Add", "Employee added", "Employee was successfully added to the database");
                    adminView.addEmployeeToTable(employeeDTO);
                } else {
                    adminView.addDisplayAlertMessage("Add Error", "Problem at adding an Employee", "There was a problem at adding the employee to the database. Please try again");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            EmployeeDTO employeeDTO = adminView.getEmployeeTable().getSelectionModel().getSelectedItem();
            if (employeeDTO != null){
                if (employeeDTO.getRole().equals("administrator")) {
                    adminView.addDisplayAlertMessage("Delete Error","Cannot Delete Administrator","You cannot delete an administrator.");
                    return;
                }
                boolean success = employeeService.delete(EmployeeMapper.convertEmployeeDTOToEmployee(employeeDTO));
                if (success){
                    adminView.addDisplayAlertMessage("Delete Successful","Employee Deleted","Employee was successfully deleted from the database");
                    adminView.removeEmployeeFromTable(employeeDTO);
                }else{
                    adminView.addDisplayAlertMessage("Delete Error","Problem at deleting a Employee","There was a problem with the database. Please try again");
                }
            } else {
                adminView.addDisplayAlertMessage("Delete Error","Problem at deleting an Employee","You must select an employee before pressing the delete button.");
            }
        }
    }

    private class GeneratePdfButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String reportParameter = "employee_report"; // Exemplu de parametrul de tip String
            employeeService.generateEmployeeSalesReport(reportParameter);
            adminView.addDisplayAlertMessage("PDF Generated", "Employee Report", "PDF has been generated successfully.");
        }
    }
}
