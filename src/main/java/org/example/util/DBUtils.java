package org.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtils {
    private final static String PROPERTY_PATH = "src/main/resources/jdbc.properties";
    private static Connection connection;

    private static Connection getConnection() {

        if (connection == null) {
            try (FileInputStream fis = new FileInputStream(PROPERTY_PATH)) {
                Properties properties = new Properties();
                properties.load(fis);
                String url = properties.getProperty("url");
                String username = properties.getProperty("username");
                String password = properties.getProperty("password");
                String driver = properties.getProperty("driver");

                connection = DriverManager.getConnection(url, username, password);

            } catch (IOException | SQLException e) {
                System.out.println("connection error: " + e);
                System.exit(1);
            }
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static PreparedStatement getPreparedStatementWithKeys(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}
