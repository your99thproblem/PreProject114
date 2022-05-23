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
        Connection conn;
        conn = Util.getInstance().getDbConnection();
        return conn;
    }

    public void createUsersTable() {

        String sqlCreate = "CREATE TABLE users "
                + "(id INTEGER NOT NULL AUTO_INCREMENT,"
                + "first VARCHAR(255), "
                + "last VARCHAR(255), "
                + "age TINYINT NOT NULL,"
                + "PRIMARY KEY ( id ))";

        try (Statement stm = getDbConnection().createStatement()) {
            stm.executeUpdate(sqlCreate);

        } catch (SQLException e) {
            System.out.println("Table is already exists");
        }
    }

    public void dropUsersTable() {

        String sqlDrop = "DROP TABLE users;";
        try (Statement stm = getDbConnection().createStatement()) {
            stm.executeUpdate(sqlDrop);

        } catch (SQLException e) {
            System.out.println("Table isn't exist");
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlSaveUser = "INSERT INTO users (first, last, age) VALUES (?, ?, ?)";
        User user = new User(name, lastName, age);

        try (PreparedStatement ps = getDbConnection().prepareStatement(sqlSaveUser)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setByte(3, user.getAge());
            ps.executeUpdate();
            System.out.println("User with name: " + user.getName() + " add to DataBase successfully");

        } catch (SQLException e) {
            System.out.println("Error while adding user");
        }
    }

    public void removeUserById(long id) {

        String sqlRemoveUserById = "DELETE FROM users WHERE id=?";

        try (PreparedStatement ps = getDbConnection().prepareStatement(sqlRemoveUserById)) {
            ps.setInt(1, (int) id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sqlGetAllUsers = "SELECT * FROM users";

        try  (PreparedStatement ps = getDbConnection().prepareStatement(sqlGetAllUsers) ){
            ResultSet resultSet = ps.executeQuery();
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
        return users;

    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users;";

        try (Statement stm = getDbConnection().createStatement()) {
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

