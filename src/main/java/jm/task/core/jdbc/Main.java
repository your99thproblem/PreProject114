package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;


public class Main {
    public static void main(String[] args) {

        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Denis", "Shkalikov", (byte) 31);
        userDao.saveUser("Jenia", "Shkalikov", (byte) 36);
        userDao.saveUser("Sergey", "Aksenov", (byte) 35);
        userDao.saveUser("Nikita", "Aksenov", (byte) 28);
        userDao.removeUserById(3);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
        System.out.println("End of program");
    }
}
