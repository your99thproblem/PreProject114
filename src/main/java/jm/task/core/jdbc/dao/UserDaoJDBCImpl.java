package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    private Connection getDbConnection() throws SQLException {
        Connection conn = Util.getInstance().getDbConnection();
        return conn;
    }

    public void createUsersTable() {

        String sqlCreate = "CREATE TABLE users "
                + "(id INTEGER NOT NULL AUTO_INCREMENT,"
                + "first VARCHAR(255), "
                + "last VARCHAR(255), "
                + "age TINYINT NOT NULL,"
                + "PRIMARY KEY ( id ))";

        try (Connection connection = getDbConnection();
             Statement stm = connection.createStatement();
             SQLCloseable finish = () -> {
                 if (!connection.getAutoCommit()) connection.rollback();
             }) {
            connection.setAutoCommit(false);
            stm.executeUpdate(sqlCreate);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Table is already exists");
        }
    }

    public void dropUsersTable() {

        String sqlDrop = "DROP TABLE users;";
        try (Connection connection = getDbConnection();
        Statement stm = connection.createStatement();
        SQLCloseable finish = () -> {
            if (!connection.getAutoCommit()) connection.rollback();
        }) {
            connection.setAutoCommit(false);
            stm.executeUpdate(sqlDrop);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Table isn't exist");
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlSaveUser = "INSERT INTO users VALUES (Null, ?, ?, ?)";
        User user = new User(name, lastName, age);

        try (Connection connection = getDbConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSaveUser);
             SQLCloseable finish = () -> {
                 if (!connection.getAutoCommit()) connection.rollback();
             }) {
            connection.setAutoCommit(false);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setByte(3, user.getAge());
            ps.executeUpdate();
            connection.commit();
            System.out.println("User with name: " + user.getName() + " add to DataBase successfully");
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("Error while adding user");
        }
    }

    public void removeUserById(long id) {

        String sqlRemoveUserById = "DELETE FROM users WHERE id=?";

        try (Connection connection = getDbConnection();
             PreparedStatement ps = connection.prepareStatement(sqlRemoveUserById);
             SQLCloseable finish = () -> {
                 if (!connection.getAutoCommit()) connection.rollback();
             }) {
            connection.setAutoCommit(false);
            ps.setInt(1, (int) id);
            ps.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sqlGetAllUsers = "SELECT * FROM users";

        try (Connection connection = getDbConnection();
             PreparedStatement ps = connection.prepareStatement(sqlGetAllUsers);
             SQLCloseable finish = () -> {
                 if (!connection.getAutoCommit()) connection.rollback();
             }) {
            connection.setAutoCommit(false);
            ResultSet resultSet = ps.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("first"));
                user.setLastName(resultSet.getString("last"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
                System.out.println(user);
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users;";

        try (Connection connection = getDbConnection();
             Statement stm = connection.createStatement();
             SQLCloseable finish = () -> {
                 if (!connection.getAutoCommit()) connection.rollback();
             }) {
            connection.setAutoCommit(false);
            stm.executeUpdate(sql);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

