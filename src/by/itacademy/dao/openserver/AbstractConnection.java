package by.itacademy.dao.openserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractConnection {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/app";
    private static final String username = "root";
    private static final String password = "root";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(connection == null) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e);
        }
        return connection;
    }

    public static void close() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }
}
