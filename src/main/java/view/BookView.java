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
import view.model.BookDTO;


import java.util.List;
//DTO =  Data Transfer Object scopuri multiple: pentru informatii confidentiale
public class BookView  extends GridPane{
    private TableView bookTableView; //construit in mod dinamic sa extraga automat ce am eu nevoie
    private final ObservableList<BookDTO> booksObservableList; //se update-aza automat daca cumva se executa modificari asupra unei tabele
    private TextField authorTextField;
    private TextField titleTextField;
   // private TextField stockTextField;
    private ComboBox<Integer> stockComboBox;
    private Label authorLabel;
    private Label titleLabel;
    private Label stockLabel;
    private Button saveButton;
    private Button deleteButton;

    private Button saleButton;
    private Scene scene;

    public BookView(Stage primaryStage, List<BookDTO> books){
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        scene=new Scene(gridPane,960,640);
        //primaryStage.setScene(scene);
        this.scene=scene;

        booksObservableList = FXCollections.observableArrayList(books);//sa nu mai facem nicaieri in cod o alta atribuire deoarece se va rupe legatura cu tableView si nu se vor mai vedea modificarile

        initTableView(gridPane);
        initSaveOptions(gridPane);
        primaryStage.show();

    }

    private void initializeGridPage(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(25,25,25,25));
    }

    private void initTableView(GridPane gridPane){
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No books to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(300);

        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(300);

        TableColumn<BookDTO, String> stockColumn = new TableColumn<BookDTO, String>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setPrefWidth(100);

        bookTableView.getColumns().addAll(titleColumn,authorColumn,stockColumn);
        bookTableView.setItems(booksObservableList);

        bookTableView.setPrefWidth(700); // Adjust based on your desired width

        // Set a resize policy to let columns expand
        bookTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel,5,1);
        stockComboBox = new ComboBox<>();
        stockComboBox.getItems().addAll(10, 15, 20, 25, 30, 50);
        stockComboBox.setPromptText("Please select stock amount");
        gridPane.add(stockComboBox, 6, 1);
        //stockTextField = new TextField();
        //gridPane.add(stockTextField, 6, 1);

        saveButton = new Button("Save");
        gridPane.add(saveButton,7,1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton,8,1);

        saleButton = new Button("Sale");
        gridPane.add(saleButton,9,1);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSaleButtonListener(EventHandler<ActionEvent> saleButtonListener){
        saleButton.setOnAction(saleButtonListener);
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

    public Integer getStock() {
        if (stockComboBox != null) {
            return stockComboBox.getValue();
        } else {
            return 0;
        }
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
