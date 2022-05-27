package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static SessionFactory sessionFactory;
    private Transaction transaction;


    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.show_sql", "true")
                        .setProperty("hibernate.connection.url", "jdbc:mysql://10.115.115.61:3306/KATA")
                        .setProperty("hibernate.connection.username", "admin")
                        .setProperty("hibernate.connection.password", "Denis_16")
                        .addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }


}


