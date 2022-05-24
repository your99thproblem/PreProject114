package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Util dbConnection = null;
    final String dbUser = "admin";
    final String dbPass = "Denis_16";
    final String dBurl = "jdbc:mysql://10.115.115.61:3306/KATA";


    public Connection getDbConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dBurl, dbUser, dbPass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static Util getInstance() {
        if (dbConnection == null) {
            dbConnection = new Util();
        }
        return dbConnection;
    }
}

