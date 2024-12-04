package model;

public class Employee {
    private Long id;
    private String username;
    private String roles;

    public Employee(Long id, String name, String role) {
        this.id = id;
        this.username = name;
        this.roles = role;
    }
    public Employee(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + roles + '\'' +
                '}';
    }
}
