package repository.book;
//o extindem si va fi el insusi un BookRepository -> decoram repository-ul cu un cache
public abstract class BookRepositoryDecorator implements BookRepository {
    protected  BookRepository decoratedBookRepository; //ca in exemplu cu matrioska
    public BookRepositoryDecorator(BookRepository bookRepository){
        decoratedBookRepository = bookRepository;
    }
}
