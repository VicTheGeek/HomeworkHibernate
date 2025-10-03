package ru.swiftvibes.util;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Victor 25.09.2025
 */

public class HibernateUtil {
    @Setter
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

    // Field getSessionFactory was wrapped to Lombok @Getter

    public static void closeSF() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // Field setSessionFactory was wrapped to Lombok @Setter

}
