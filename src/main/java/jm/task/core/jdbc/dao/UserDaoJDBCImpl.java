package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private List<User> users = new ArrayList<>();
    private final Connection dbconnection = getDbConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable()  {
        String sql = "CREATE TABLE users "
                + "(id INTEGER NOT NULL AUTO_INCREMENT,"
                + "first VARCHAR(255), "
                + "last VARCHAR(255), "
                + "age TINYINT NOT NULL,"
                + "PRIMARY KEY ( id ))";

        try (Statement stm  = dbconnection.createStatement()) {
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {

    }

    public void saveUser(String name, String lastName, byte age) {

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
