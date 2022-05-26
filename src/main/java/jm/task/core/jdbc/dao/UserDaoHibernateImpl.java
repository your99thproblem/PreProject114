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
        Transaction tr = null;
        String sqlCreate = "CREATE TABLE IF NOT EXIST users "
                + "(id INTEGER NOT NULL AUTO_INCREMENT, "
                + "first VARCHAR(255), "
                + "last VARCHAR(255), "
                + "age TINYINT NOT NULL, "
                + "PRIMARY KEY ( id ))";
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(sqlCreate);
            tr.commit();


        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            System.out.println("error while creating table");
        }


    }

    @Override
    public void dropUsersTable() {
        Transaction tr = null;
        String sqlDrop = "DROP TABLE users";
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(sqlDrop).executeUpdate();
            tr.commit();


        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            System.out.println("error while drop table");

        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.save(user);
            tr.commit();
            System.out.println("User with name: " + user.getName() + " add to DataBase successfully");

        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            System.out.println("Error while adding user");
        }


    }

    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        }


    }

    @Override
    public List<User> getAllUsers() {
        Transaction tr = null;
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            Query<User> query = session.createQuery(cq);
            users = query.getResultList();
//            users = session.createCriteria(User.class).list();
            tr.commit();
            for (Iterator<User> it = users.iterator(); it.hasNext(); ) {
                User user = it.next();
                System.out.println(user);

            }
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
            };
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            tr.commit();

        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
            }
            System.out.println("error while truncating table");
        }


    }
}
