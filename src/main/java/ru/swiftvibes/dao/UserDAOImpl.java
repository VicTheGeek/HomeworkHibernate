package ru.swiftvibes.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.swiftvibes.entity.User;
import ru.swiftvibes.util.HibernateUtil;

// LOGS
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Victor 25.09.2025
 */

public class UserDAOImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public void create(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            log.info("user {} was created successfully", user.getName());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error(e.getMessage());
        }
    }

    @Override
    public User read(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        }
    }

    @Override
    public List<User> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }

    @Override
    public void update(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
            log.info("user {} was updated successfully", user.getName());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            tx.commit();
            log.info("user {} was deleted successfully", user.getName());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createMutationQuery("delete from User").executeUpdate();
            tx.commit();
            log.info("all users were deleted successfully");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error(e.getMessage());
        }
    }
}
