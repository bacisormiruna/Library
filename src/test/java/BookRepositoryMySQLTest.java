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

    //asigur conexiunea cu baza de date
    @BeforeAll
    public static void setup() {//pentru ca setup este o metoda statica trebuie sa fac si connection static, daca modific metoda si sterg static pot sa am si connection fara static
        connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();//setez pe true ca sa folosesc tabela de test
        if (connection != null) {
            bookRepository = new BookRepositoryMySQL(connection);
            bookRepository.removeAll(); //la fiecare test tabela va fi goala
        } else {
            System.out.println("Connection failed!");
        }
    }

    @Test
    public void findAll(){
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void findById(){
        //dupa ce am creat si adaugat cartea in tabela, iau toate id-urile si stochez id-ul primei ( si singurei valori din acest test) carti
        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1900, 10, 10))
                .build();

        assertTrue(bookRepository.save(book));

        List<Book> books = bookRepository.findAll();
        Long id = books.get(0).getId();

        Optional<Book> foundBook = bookRepository.findById(id);
        assertTrue(foundBook.isPresent()); //verific daca am acel id
    }

    @Test
    public void saveBook1(){
        Book book = new BookBuilder()
                .setTitle("Fluturi")
                .setAuthor("Irina Binder")
                .setPublishedDate(LocalDate.of(1950, 2, 2))
                .build();
        assertTrue(bookRepository.save(book)); //daca s-a salvat cartea cu succes
        List<Book> books = bookRepository.findAll();//dupa ce am adaugat-o in tabela iau lista cu carti si verific daca s-a adaugat cu succes prin verificarea lungimii tabelei (listei de carti)
        assertEquals(1, books.size());//verific daca s-a adaugat o singura carte
    }

    @Test
    public void saveBook2(){
        Book book = new BookBuilder()
                .setTitle("Un veac de singuratate")
                .setAuthor("Gabriel Garcia Marquez")
                .setPublishedDate(LocalDate.of(1940, 3, 10))
                .build();
        assertTrue(bookRepository.save(book));
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());//se face verificarea adaugarii corecte a cartii in baza de date
    }

    @Test
    public void delete() {
        Book book = new BookBuilder() //creez o carte pe care o sa o pot sterge pentru a testa
                .setTitle("Enigma Otiliei")
                .setAuthor("George Călinescu")
                .setPublishedDate(LocalDate.of(1938, 1, 1))
                .build();

        assertTrue(bookRepository.save(book));//am salvat cartea
        assertTrue(bookRepository.delete(book));//am sters-o
        Optional<Book> deletedBook = bookRepository.findById(book.getId());//acum caut sa vad daca mai exista in tabela
        assertFalse(deletedBook.isPresent());
    }
}