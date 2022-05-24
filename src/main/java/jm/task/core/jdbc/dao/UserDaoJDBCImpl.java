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

        try (Connection connection = getDbConnection()) {
            try (Statement stm = connection.createStatement()) {
                connection.setAutoCommit(false);
                stm.executeUpdate(sqlCreate);
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Table is already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String sqlDrop = "DROP TABLE users;";
        try (Connection connection = getDbConnection()) {
            try (Statement stm = connection.createStatement()) {
                connection.setAutoCommit(false);
                stm.executeUpdate(sqlDrop);
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Table isn't exist");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlSaveUser = "INSERT INTO users VALUES (Null, ?, ?, ?)";
        User user = new User(name, lastName, age);

        try (Connection connection = getDbConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlSaveUser)) {
                connection.setAutoCommit(false);
                ps.setString(1, user.getName());
                ps.setString(2, user.getLastName());
                ps.setByte(3, user.getAge());
                ps.executeUpdate();
                connection.commit();
                System.out.println("User with name: " + user.getName() + " add to DataBase successfully");

            } catch (SQLException e) {
                System.out.println("Error while adding user");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String sqlRemoveUserById = "DELETE FROM users WHERE id=?";

        try (Connection connection = getDbConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlRemoveUserById)) {
                connection.setAutoCommit(false);
                ps.setInt(1, (int) id);
                ps.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sqlGetAllUsers = "SELECT * FROM users";

        try (Connection connection = getDbConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlGetAllUsers)) {
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

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users;";

        try (Connection connection = getDbConnection()) {
            try (Statement stm = connection.createStatement()) {
                connection.setAutoCommit(false);
                stm.executeUpdate(sql);
                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

