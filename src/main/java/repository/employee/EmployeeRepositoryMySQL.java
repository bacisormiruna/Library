package repository.employee;

import model.Employee;
import model.Role;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.user.AuthentificationServiceMySQL;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryMySQL implements EmployeeRepository {
    private final Connection connection;

    public EmployeeRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

   /* @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("username")
                        //resultSet.getString("role")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }*/
   @Override
   public List<Employee> findAll() {
       List<Employee> employees = new ArrayList<>();
       String userQuery = "SELECT id, username FROM user";
       String rolesQuery = "SELECT role_id FROM user_role WHERE user_id = ?";
       String roleQuery = "SELECT role FROM role WHERE id = ?";

       try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
           ResultSet userResultSet = userStatement.executeQuery();

           while (userResultSet.next()) {
               long userId = userResultSet.getLong("id");
               String username = userResultSet.getString("username");

               String role = null;
               try (PreparedStatement rolesStatement = connection.prepareStatement(rolesQuery)) {
                   rolesStatement.setLong(1, userId);
                   ResultSet rolesResultSet = rolesStatement.executeQuery();

                   if (rolesResultSet.next()) {
                       long roleId = rolesResultSet.getLong("role_id");
                       try (PreparedStatement roleStatement = connection.prepareStatement(roleQuery)) {
                           roleStatement.setLong(1, roleId);
                           ResultSet roleResultSet = roleStatement.executeQuery();
                           if (roleResultSet.next()) {
                               role = roleResultSet.getString("role");
                           }
                       }
                   }
               }
               employees.add(new Employee(userId, username, role));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }

       return employees;
   }

    @Override
    public List<Employee> findEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT u.id AS user_id, u.username AS username FROM user u INNER JOIN user_role ur ON u.id = ur.user_id  INNER JOIN role r ON ur.role_id = r.id WHERE r.role = 'employee' ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                String username = resultSet.getString("username");
                employees.add(new Employee(userId, username, "employee"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }


    @Override
    public boolean add(Employee employee) {
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String hashedPassword = hashPassword("defaultPassword2!");

            statement.setString(1, employee.getUsername());
            statement.setString(2, hashedPassword);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);

                        String role = employee.getRoles();
                        if (role != null) {
                            String roleQuery = "SELECT id FROM role WHERE role = ?";
                            try (PreparedStatement roleStatement = connection.prepareStatement(roleQuery)) {
                                roleStatement.setString(1, role);
                                ResultSet roleResultSet = roleStatement.executeQuery();
                                if (roleResultSet.next()) {
                                    long roleId = roleResultSet.getLong("id");
                                    String userRoleQuery = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";
                                    try (PreparedStatement userRoleStatement = connection.prepareStatement(userRoleQuery)) {
                                        userRoleStatement.setLong(1, userId);
                                        userRoleStatement.setLong(2, roleId);
                                        userRoleStatement.executeUpdate();
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private String hashPassword(String password) {
        try {
            //Secured Hash Algorithm - 256
            //1 byte = 8 biti
            //1 byte =1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean delete(Employee employee) {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, employee.getId());

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public int getNumberOfBooksSoldPerEmployee(Long employeeId) {
        String sql = "SELECT SUM(number_of_exemplars) FROM orders WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total books sold: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double getTotalSumOfBooks(Long employeeId) {
        String sql = "SELECT SUM(price * number_of_exemplars) FROM orders WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total sales value: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0;
    }
}
