package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {

//        UserDao userDao = new UserDaoJDBCImpl();
        UserDao userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Denis", "Shkalikov", (byte) 31);
        userDaoHibernate.saveUser("Jenia", "Shkalikov", (byte) 36);
        userDaoHibernate.saveUser("Sergey", "Aksenov", (byte) 35);
        userDaoHibernate.saveUser("Nikita", "Aksenov", (byte) 28);
        userDaoHibernate.removeUserById(5);
        userDaoHibernate.getAllUsers();
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
//        userDao.createUsersTable();
//        userDao.saveUser("Denis", "Shkalikov", (byte) 31);
//        userDao.saveUser("Jenia", "Shkalikov", (byte) 36);
//        userDao.saveUser("Sergey", "Aksenov", (byte) 35);
//        userDao.saveUser("Nikita", "Aksenov", (byte) 28);
//        userDao.removeUserById(3);
//        userDao.getAllUsers();
//        userDao.cleanUsersTable();
//        userDao.dropUsersTable();
        System.out.println("End of program");

//        Util.getSessionFactory();
    }
}
