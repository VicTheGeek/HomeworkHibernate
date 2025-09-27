package ru.swiftvibes.service;

import ru.swiftvibes.dao.UserDAOImpl;
import ru.swiftvibes.dao.UserDao;
import ru.swiftvibes.entity.User;

import java.util.List;

/**
 * Created by Victor 25.09.2025
 */

public class UserService {
    private UserDao userDao = new UserDAOImpl();

    public void createUser(User user) {
        userDao.create(user);
    }

    public User getUserById(Long id) {
        return userDao.read(id);
    }

    public List<User> getAllUsers() {
        return userDao.readAll();
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    public void deleteAllUsers() {
        userDao.deleteAll();
    }

}
