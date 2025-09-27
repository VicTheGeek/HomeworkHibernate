package ru.swiftvibes.dao;

import ru.swiftvibes.entity.User;

import java.util.List;

/**
 * Created by Victor 25.09.2025
 */

// data access object
public interface UserDao {
    void create(User user);
    User read(Long id);
    List<User> readAll();
    void update(User user);
    void delete(Long id);
    void deleteAll();
}
