package ru.swiftvibes.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Victor 25.09.2025
 */

public class HibernateUtil {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("session factory creation failed: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    // Field 'sessionFactory' was wrapped to Lombok @Getter

    public static void closeSF() {
        sessionFactory.close();
    }
}
