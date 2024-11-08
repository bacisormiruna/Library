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
import javafx.stage.Stage;
import model.Book;
import view.model.BookDTO;


import java.util.List;
//DTO Data Transfer Object scopuri multiple: pentru informatii confidentiale
public class BookView {
    private TableView bookTableView; //construit in mod dinamic sa extraga automat ce am eu nevoie
    private final ObservableList<BookDTO> booksObservableList; //se update-aza automat daca cumva se executa modificari asupra unei tabele
    private TextField authorTextField;
    private TextField titleTextField;

    private Label authorLabel;
    private Label titleLabel;
    private Button saveButton;
    private Button deleteButton;

    public BookView(Stage primaryStage, List<BookDTO> books){
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene=new Scene(gridPane,720,480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);//sa nu mai facem nicaieri in cod o alta atribuire deoarece se va rupe legatura cu tableView si nu se vor mai vedea modificarile

        initTableView(gridPane);
        initSaveOptions(gridPane);
        primaryStage.show();

    }

    private void initializeGridPage(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
    }

    private void initTableView(GridPane gridPane){
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No books to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        bookTableView.getColumns().addAll(titleColumn,authorColumn);
        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView,0,0,5,1);
    }

    private void initSaveOptions(GridPane gridPane){
        titleLabel = new Label("Title");
        gridPane.add(titleLabel,1,1);
        titleTextField=new TextField();
        gridPane.add(titleTextField,2,1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel,3,1);
        authorTextField=new TextField();
        gridPane.add(authorTextField,4,1);

        saveButton = new Button("Save");
        gridPane.add(saveButton,5,1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton,6,1);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String contentInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentInfo);
        alert.showAndWait();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public void addBookToObservableList(BookDTO bookDTO){
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookToObservableList(BookDTO bookDTO){
        this.booksObservableList.remove(bookDTO);
    }

    public TableView getBookTableView(){
        return bookTableView;
    }
}
