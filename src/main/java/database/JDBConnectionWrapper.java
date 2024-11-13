package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DB_URL="jdbc:mysql://localhost/";
    private static final String USER="root";
    private static final String PASSWORD="MirunaB2024";//mi s-a cerut la instalare

    private static final int TIMEOUT=5;

    private Connection connection;

    public JDBConnectionWrapper(String schema){
        try{
            Class.forName(JDBC_DRIVER);//reflection
            connection= DriverManager.getConnection(DB_URL + schema, USER,PASSWORD);
            createTables();//sa ne creeze tabela
        }catch (ClassNotFoundException e){ //cele mai specifice
            e.printStackTrace();
        }catch(SQLException e){//cele mai generale
            e.printStackTrace();
        }

    }
    private void createTables() throws SQLException{
        Statement statement=connection.createStatement();
        String sql="CREATE TABLE IF NOT EXISTS book("+
                " id bigint NOT NULL AUTO_INCREMENT," +
                " author VARCHAR(500) NOT NULL," +
                " title VARCHAR(500) NOT NULL," +
                " publishedDate datetime DEFAULT NULL," +
                " stock INT DEFAULT 0 NOT NULL," +
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";
        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException{//testam daca am avut sau nu o conexiune
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection(){//returnam conexiunea cu baza de date
        return connection;
    }
}
