package launcher;

import controller.AdminController;
import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import mapper.EmployeeMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.employee.EmployeeRepository;
import repository.employee.EmployeeRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceImpl;
import view.AdminView;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {
    private final AdminView adminView;
    private final AdminController adminController;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private static AdminComponentFactory instance;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
        if (instance == null) {
            synchronized (AdminComponentFactory.class) {
                if (instance == null) {
                    instance = new AdminComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    private AdminComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.employeeRepository = new EmployeeRepositoryMySQL(connection);
        this.employeeService = new EmployeeServiceImpl(employeeRepository);
        this.adminView = new AdminView(primaryStage, EmployeeMapper.convertEmployeeListToEmployeeDTOList(employeeRepository.findAll()));
        this.adminController = new AdminController(adminView, employeeService);
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }
}
