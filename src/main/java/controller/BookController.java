package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import mapper.BookMapper;
import service.book.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.util.Optional;

public class BookController {
    private final BookView bookView;
    private final BookService booksService;
    //private final Stage stage;
    public BookController(BookView bookView, BookService bookService){
        this.bookView=bookView;
        this.booksService=bookService;
        //this.stage=stage;
        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSaleButtonListener(new SaleButtonListener());
    }
    //inner class privat
    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title= bookView.getTitle();
            String author=bookView.getAuthor();
            Integer stock = bookView.getStock(); // Convertim textul în întreg

            if(title.isEmpty() || author.isEmpty() || stock==null){
                bookView.addDisplayAlertMessage("Save Error","Problem at Author, Title or Stock fields","Cannot have an empty field");
            }else{
                BookDTO bookDTO= new BookDTOBuilder().setTitle(title).setAuthor(author).setStock(stock).build();
                boolean savedBook = booksService.save(BookMapper.convertBookDTOToBook(bookDTO));
                if (savedBook){
                    bookView.addDisplayAlertMessage("Save Successful","Book Added","Book was successfully added to the database!");
                    bookView.addBookToObservableList(bookDTO);
                }else{
                    bookView.addDisplayAlertMessage("Save Error","Problem at adding a Book","There was a problem at adding the book to the database. Please try again");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessful = booksService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessful){
                    bookView.addDisplayAlertMessage("Delete Successful","Book Deleted","Book was successfully deleted from the database");
                    bookView.removeBookToObservableList(bookDTO);
                }else{
                    bookView.addDisplayAlertMessage("Delete Error","Problem at deleting a Book","There was a problem with the database. Please try again");
                }
            }else{
                bookView.addDisplayAlertMessage("Delete Error","Problem at deleting a Book","You must select a book before pressing the delete button.");
            }
        }
    }

    private class SaleButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();

            if (bookDTO != null) {
                Long bookId = bookDTO.getId();
                if (bookId == null) {
                    bookView.addDisplayAlertMessage("Error", "Invalid Book", "The selected book has an invalid ID.");
                    return;
                }

                TextInputDialog fereastra = new TextInputDialog();//dupa ce apas pe butonul de sale si inainte am selectat o carte sa mi se deschida o fereastra care sa imi ceara cantitatea pe care vreau sa o vand
                fereastra.setTitle("Selling book(s)");
                fereastra.setHeaderText("Please enter quantity to sell");
                fereastra.setContentText("Quantity:");

                Optional<String> res = fereastra.showAndWait();//astept pana se inchide de user fereastra
                if (res.isPresent()) {
                    try {
                        int quantity = Integer.parseInt(res.get());
                        if (quantity <= 0) {
                            bookView.addDisplayAlertMessage("Input Error", "Invalid Quantity", "Please enter a positive quantity.");
                            return;
                        }
                        boolean saleSuccessful = booksService.sale(bookId, quantity);

                        if (saleSuccessful) {
                            bookDTO.setStock(bookDTO.getStock() - quantity);//update la stoc
                            bookView.addDisplayAlertMessage("Sale Successful", "Book Sold", "Book was successfully sold!");
                            bookView.getBookTableView().refresh();  // sa se vada modificarile, scaderea stocului dupa vanzare
                        } else {
                            bookView.addDisplayAlertMessage("Sale Error", "Insufficient Stock", "There are not enough books in stock.");
                        }
                    } catch (NumberFormatException e) {
                        bookView.addDisplayAlertMessage("Input Error", "Invalid Quantity", "Please enter a valid number.");
                    }
                }
            } else {
                bookView.addDisplayAlertMessage("Sale Error", "No Book Selected", "You must select a book before pressing the sale button.");
            }
        }
    }}