package ru.swiftvibes.dao;

import java.time.LocalDate;
import java.util.List;

import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.swiftvibes.entity.User;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Victor 01.10.2025
 */

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // init only once
@NoArgsConstructor
public class UserDAOTest {
    @Container
    private final PostgreSQLContainer<?> psqlcontainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testDB")
            .withUsername("postgres")
            .withPassword("postgres");

    private UserDao userDao;

    @BeforeAll
    void startContainers() {
        psqlcontainer.start();
    }

    @AfterAll
    void stopContainers() {
        psqlcontainer.stop();
    }

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", psqlcontainer.getJdbcUrl())
                .setProperty("hibernate.connection.username", psqlcontainer.getUsername())
                .setProperty("hibernate.connection.password", psqlcontainer.getPassword())
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        HibernateTestUtil.overrideSessionFactory(sessionFactory);

        userDao = new UserDAOImpl();
    }

    @AfterEach
    void cleanDB() {
        userDao.deleteAll();
    }

    @Test
    public void сreateUserTest() {
        User user = new User("test", "aa@aa.aa", 22, LocalDate.now());
        userDao.create(user);

        User found = userDao.read(user.getId());
        assertNotNull(found);
        assertEquals("test", found.getName());
    }

    @Test
    public void readUserTest() {
        User user = new User("test", "aa@aa.aa", 22, LocalDate.now());
        userDao.create(user);

        User found = userDao.read(user.getId());
        assertNotNull(found);
        assertEquals("test", found.getName());
        assertEquals("aa@aa.aa", found.getEmail());
        assertEquals(22, found.getAge());
        // FIXME: how to assert date time
    }

    @Test
    public void readAllUserTest() {
        userDao.create(new User("test", "1aa@aa.aa", 22, LocalDate.now()));
        userDao.create(new User("test1", "2aa@aa.aa", 21, LocalDate.now()));

        userDao.readAll();
        List<User> users = userDao.readAll();

        assertFalse(users.isEmpty());
    }

    @Test
    public void updateUserTest() {
        User user = new User("test", "aa@aa.aa", 22, LocalDate.now());
        userDao.create(user);

        user.setName("updated");
        userDao.update(user);
        User found = userDao.read(user.getId());
        assertNotNull(found);
        assertEquals("updated", found.getName());
    }

    @Test
    public void deleteUserTest() {
        User user = new User("test", "aa@aa.aa", 22, LocalDate.now());
        userDao.create(user);

        userDao.delete(user.getId());
        User deleted = userDao.read(user.getId());
        assertNull(deleted);
    }

    @Test
    public void deleteAllUserTest() {
        userDao.create(new User("test", "1aa@aa.aa", 22, LocalDate.now()));
        userDao.create(new User("test1", "2aa@aa.aa", 21, LocalDate.now()));

        userDao.deleteAll();
        List<User> users = userDao.readAll();
        assertTrue(users.isEmpty());
    }

}
