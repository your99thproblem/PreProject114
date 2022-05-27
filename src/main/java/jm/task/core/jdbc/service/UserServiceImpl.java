package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao hbntUser = new UserDaoHibernateImpl();

    public void createUsersTable() throws SQLException {
        hbntUser.createUsersTable();

    }

    public void dropUsersTable() {
        hbntUser.dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        hbntUser.saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        hbntUser.removeUserById(id);

    }

    public List<User> getAllUsers() {
        return hbntUser.getAllUsers();
    }

    public void cleanUsersTable() {
        hbntUser.cleanUsersTable();

    }
}
