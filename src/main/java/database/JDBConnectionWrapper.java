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
                " price DECIMAL(10, 2) DEFAULT 0," +
                " stock INT DEFAULT 0 NOT NULL," +
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";
        statement.execute(sql);

        String ordersTableSql = "CREATE TABLE IF NOT EXISTS orders (" +
                " id BIGINT NOT NULL AUTO_INCREMENT," +
                " user_id INT NOT NULL," +
                " title VARCHAR(500) NOT NULL," +
                " author VARCHAR(500) NOT NULL," +
                " total_price DECIMAL(10, 2) NOT NULL," +
                " number_of_exemplars INT NOT NULL," +
                " sale_date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                " PRIMARY KEY (id)," +
                " FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
        statement.execute(ordersTableSql);
    }

    public boolean testConnection() throws SQLException{//testam daca am avut sau nu o conexiune
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection(){//returnam conexiunea cu baza de date
        return connection;
    }
}