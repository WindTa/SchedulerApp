package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost/J&K";
    private static String username = "root";
    private static String password = "";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to server.");
        }
        return con;
    }
}