package ru.swiftvibes.dao;

import org.hibernate.SessionFactory;
import ru.swiftvibes.util.HibernateUtil;

/**
 * Created by Victor 02.10.2025
 */
public class HibernateTestUtil {
    public static void overrideSessionFactory(SessionFactory sessionFactory) {
        HibernateUtil.setSessionFactory(sessionFactory);
    }
}
