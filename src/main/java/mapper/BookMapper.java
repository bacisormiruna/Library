package mapper;

import model.Book;
import model.builder.BookBuilder;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDTO convertBookToBookDTO(Book book){//am adaugat si setId pentru ca am nevoie sa mi se stocheze in baza de date valorile pentru id ca sa pot tine minte stocul si id-ul cartii pe care o updatez
        return new BookDTOBuilder().setId(book.getId()).setTitle(book.getTitle()).setAuthor(book.getAuthor()).setPrice(book.getPrice()).setStock(book.getStock()).build();
    }

    public static Book convertBookDTOToBook(BookDTO bookDTO){
        return new BookBuilder().setId(bookDTO.getId()).setTitle(bookDTO.getTitle()).setAuthor(bookDTO.getAuthor()).setPublishedDate(LocalDate.of(2010,1,1)).setPrice(bookDTO.getPrice()).setStock(bookDTO.getStock()).build();
    }

    public static List<BookDTO> convertBookListToBookDTOList(List<Book> books){
        return books.parallelStream().map(BookMapper::convertBookToBookDTO).collect(Collectors.toList());
    }

    public static List<Book> convertBookDTOToBookList(List<BookDTO>  bookDTOS){
        return bookDTOS.parallelStream().map(BookMapper::convertBookDTOToBook).collect(Collectors.toList());
    }

}
