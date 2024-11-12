package repository;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository{
    private Connection connection;
    //injectam din exterior aceasta conexiune

    public BookRepositoryMySQL(Connection connection){
        this.connection=connection;
    }
    @Override
    public List<Book> findAll() {
        String sql="SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

   /* @Override  --functie de findById ca sa ma asigur ca input-urile sunt corecte
    public Optional<Book> findById(Long id) { //am adaugat si aici preparedStatement deoarece se introduce un input si nu vream sa riscam sa fie o comanda care sa afecteze tabela
        String sql = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id); // Setează valoarea ID-ului
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // Dacă există un rezultat, construiește obiectul Book
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }*/

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = " + id;
        Optional<Book> book = Optional.empty();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) { // Dacă există un rezultat, construiește obiectul Book
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }



    @Override
    public boolean save(Book book) {
        //String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() + "\', \'" + book.getTitle() +"\', \'" + book.getPublishedDate()+ "\');";
          String newSql = "INSERT INTO book VALUES(null, ?, ?, ?);";
        try{
            //Statement statement= connection.createStatement();
            //statement.executeUpdate(newSql);
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            int rowsInserted = preparedStatement.executeUpdate();
            return (rowsInserted != 1) ? false : true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Book book) {
        String newSql="DELETE FROM book WHERE author=\'" +book.getAuthor() + "\' AND title=\'" +book.getTitle() + "\';";
        try{
            Statement statement= connection.createStatement();
            statement.executeUpdate(newSql);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String sql="DELETE FROM book WHERE id >=0;";//id-urile continua de la id-ul curent sters sa se autoincrementeze
        try{
            Statement statement= connection.createStatement();
            statement.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();}
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .build();
    }
}
