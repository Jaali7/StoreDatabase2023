

import java.sql.*;

public class database {
    public static void main(String[] args) {
        database.connect();
    }


    public static Connection connection;

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/online_store?serverTimezone=EST", "root", "password");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    database.connection.close();
                    System.out.println("Application Closed - DB Connection Closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, "Shutdown-thread"));
    }
}