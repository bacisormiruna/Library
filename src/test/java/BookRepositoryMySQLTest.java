import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryMock;
import repository.BookRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySQLTest {
    private static BookRepository bookRepository;
    private static Connection connection;

    @BeforeEach
    public void setup() {
        connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();//setez pe true ca sa folosesc tabela de test
        if (connection != null) {
            bookRepository = new BookRepositoryMySQL(connection);
            bookRepository.removeAll();
        } else {
            System.out.println("Connection failed!");
        }
    }

    @Test
    public void findAll(){
        // Verificăm dacă findAll returnează lista corectă
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
       // assertEquals("Ion", books.get(0).getTitle());
    }

    @Test
    public void findById(){
        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1900, 10, 10))
                .build();

         assertTrue(bookRepository.save(book));

        // Găsim cartea după ID și verificăm detaliile
        List<Book> books = bookRepository.findAll();
        Long id = books.get(0).getId();

        Optional<Book> foundBook = bookRepository.findById(id);
        assertTrue(foundBook.isPresent());
        assertEquals("Ion", foundBook.get().getTitle());
        assertEquals("Liviu Rebreanu", foundBook.get().getAuthor());
    }

    @Test
    public void save(){
        Book book = new BookBuilder()
                .setTitle("Fluturi")
                .setAuthor("Irina Binder")
                .setPublishedDate(LocalDate.of(2020, 6, 2))
                .build();
        // Verificăm că salvarea s-a realizat cu succes
        assertTrue(bookRepository.save(book));
        // Verificăm că baza de date conține acum un singur rezultat
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
        assertEquals("Fluturi", books.get(0).getTitle());
    }

    @Test
    public void delete() {
        // salvam o carte de test pentru a testa si stergerea
        Book book = new BookBuilder()
                .setTitle("Enigma Otiliei")
                .setAuthor("George Călinescu")
                .setPublishedDate(LocalDate.of(1938, 1, 1))
                .build();
        assertTrue(bookRepository.save(book));
        // se face stergerea si o verific
        assertTrue(bookRepository.delete(book));
        // verificam daca mai exista cartea in baza de date
        Optional<Book> deletedBook = bookRepository.findById(book.getId());
        assertFalse(deletedBook.isPresent());
    }

}

