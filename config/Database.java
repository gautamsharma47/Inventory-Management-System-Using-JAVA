package config;
import java.sql.*;

public class Database {
    public static Connection connect() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Replace 'your_actual_password' with your real MySQL password
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bca_inventory", "root", "gautam2005");
    } catch (Exception e) {
        System.out.println("Database Connection Error: " + e.getMessage());
        return null;
    }
}
}