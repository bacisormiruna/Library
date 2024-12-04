package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import view.model.EmployeeDTO;

import java.util.List;

public class AdminView {
    private final TableView<EmployeeDTO> tableView;
    private final ObservableList<EmployeeDTO> employeesObservableList;
    private TextField nameTextField;
    private TextField roleTextField;
    private Button addButton;
    private Button deleteButton;
    private Button generatePdfButton;

    public AdminView(Stage primaryStage, List<EmployeeDTO> employees) {
        primaryStage.setTitle("Administrator View");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        employeesObservableList = FXCollections.observableArrayList(employees);

        tableView = new TableView<>();
        setupTable(gridPane);
        setupForm(gridPane);
        setupGeneratePdfButton(gridPane);

        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));
    }

    private void setupTable(GridPane gridPane) {
        TableColumn<EmployeeDTO, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<EmployeeDTO, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<EmployeeDTO, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableView.getColumns().addAll(idColumn, nameColumn, roleColumn);
        tableView.setItems(employeesObservableList);
        tableView.setPlaceholder(new Label("No employees to display"));
        tableView.setPrefWidth(600);

        gridPane.add(tableView, 0, 0, 3, 1);
    }

    private void setupForm(GridPane gridPane) {
        Label nameLabel = new Label("Name:");
        gridPane.add(nameLabel, 0, 1);
        nameTextField = new TextField();
        gridPane.add(nameTextField, 1, 1);

        Label roleLabel = new Label("Role:");
        gridPane.add(roleLabel, 0, 2);
        roleTextField = new TextField();
        gridPane.add(roleTextField, 1, 2);

        addButton = new Button("Add");
        gridPane.add(addButton, 0, 3);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 1, 3);
    }

    private void setupGeneratePdfButton(GridPane gridPane) {
        generatePdfButton = new Button("Generate PDF");
        HBox hBox = new HBox(generatePdfButton);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setHgrow(generatePdfButton, Priority.ALWAYS);
        gridPane.add(hBox, 2,3);
    }

    public void addAddButtonListener(EventHandler<ActionEvent> addButtonListener) {
        addButton.setOnAction(addButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addGeneratePdfButtonListener(EventHandler<ActionEvent> generatePdfButtonListener) {
        generatePdfButton.setOnAction(generatePdfButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String contentInfo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentInfo);
        alert.showAndWait();
    }

    public String getEmployeeName() {
        return nameTextField.getText();
    }

    public String getEmployeeRole() {
        return roleTextField.getText();
    }

    public void addEmployeeToTable(EmployeeDTO employee) {
        employeesObservableList.add(employee);
    }

    public TableView<EmployeeDTO> getEmployeeTable() {
        return tableView;
    }
    public void removeEmployeeFromTable(EmployeeDTO employee) {
        employeesObservableList.remove(employee);
    }
}
