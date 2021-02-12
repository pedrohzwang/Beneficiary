package db;

import exceptions.DBException;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/beneficiary","postgres","123");
        } catch (Exception e) {
            throw new DBException(e.getMessage());
        }
        return conn;
    }


}
