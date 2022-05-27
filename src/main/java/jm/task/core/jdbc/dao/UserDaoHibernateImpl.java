package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import net.bytebuddy.asm.MemberSubstitution;
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
        SessionFactory sf = Util.getSessionFactory();//+
        String sqlCreate = "CREATE TABLE IF NOT EXISTS users "
                + "(id INTEGER NOT NULL AUTO_INCREMENT, "
                + "first VARCHAR(255), "
                + "last VARCHAR(255), "
                + "age TINYINT NOT NULL, "
                + "PRIMARY KEY ( id ))";
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlCreate).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();


        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            System.out.println("error while creating table");
        }


    }

    @Override
    public void dropUsersTable() {//+
        SessionFactory sf = Util.getSessionFactory();//+
        String sqlDrop = "DROP TABLE IF EXISTS users";
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlDrop).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();


        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            System.out.println("error while drop table");

        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {//+
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User with name: " + name + " add to DataBase successfully");

        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            System.out.println("Error while adding user");
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            System.out.println("Error while removing user");
        }


    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            for (Iterator<User> it = users.iterator(); it.hasNext(); ) {
                User user = it.next();
                System.out.println(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            System.out.println("Error while getting users");

        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("delete FROM User").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            System.out.println("error while truncating table");
        }

    }
}
