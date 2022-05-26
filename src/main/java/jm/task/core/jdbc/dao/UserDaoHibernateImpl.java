package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.SQLGrammarException;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tr;
        String sqlCreate = "CREATE TABLE IF NOT EXISTS users "
                + "(id INTEGER NOT NULL AUTO_INCREMENT, "
                + "first VARCHAR(255), "
                + "last VARCHAR(255), "
                + "age TINYINT NOT NULL, "
                + "PRIMARY KEY ( id ))";
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(sqlCreate).addEntity(User.class).executeUpdate();
            tr.commit();


        } catch (Exception e) {
            System.out.println("error while creating table");
        }


    }

    @Override
    public void dropUsersTable() {
        Transaction tr;
        String sqlDrop = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(sqlDrop).addEntity(User.class).executeUpdate();
            tr.commit();


        } catch (Exception e) {
            System.out.println("error while drop table");

        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr;

        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
            System.out.println("User with name: " + name + " add to DataBase successfully");

        } catch (Exception e) {
            System.out.println("Error while adding user");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tr;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            tr.commit();
        } catch (Exception e) {
            System.out.println("Error while removing user");
        }


    }

    @Override
    public List<User> getAllUsers() {
        Transaction tr;
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Query<User> query = session.createQuery(cq);
            users = query.getResultList();
            tr.commit();
            for (Iterator<User> it = users.iterator(); it.hasNext(); ) {
                User user = it.next();
                System.out.println(user);

            }
        } catch (Exception e) {
            System.out.println("Error while getting users");

        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            tr.commit();

        } catch (Exception e) {
            System.out.println("Error while cleaning table");
        }


    }
}
