package jm.task.core.jdbc.util;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Util {
    private String dbUser = "admin";
    private String dbPass = "Denis_16";
    private String url = "jdbc:mysql://10.115.115.61:3306/KATA";


    private Connection dbConnection = null;

    public Connection getDbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, dbUser, dbPass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }
}

