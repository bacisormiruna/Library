package view.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Role;

import java.util.List;

public class EmployeeDTO {
    private LongProperty idProperty;
    private StringProperty username;
    private StringProperty role;

    public EmployeeDTO() {
        this.idProperty = new SimpleLongProperty(this, "id");
        this.username = new SimpleStringProperty(this, "username");
        this.role = new SimpleStringProperty(this, "role", "employee"); // Setăm un rol implicit
    }

    public void setId(Long id){
        idProperty.set(id);
    }

    public Long getId(){
        return idProperty.get();
    }

    public LongProperty idProperty(){
        return idProperty;
    }

    public void setUsername(String username){
        usernameProperty().set(username);
    }

    public String getUsername(){
        return usernameProperty().get();
    }

    public StringProperty usernameProperty(){
        return username;
    }

    public void setRoles(String roles) {
        if (roles != null && !roles.isEmpty()) {
            role.set(roles);
        } else {
            role.set("customer");
        }
    }
    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }
}
