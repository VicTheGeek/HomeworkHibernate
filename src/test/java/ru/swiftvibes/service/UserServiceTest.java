package ru.swiftvibes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.swiftvibes.dao.UserDao;
import ru.swiftvibes.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

/**
 * Created by Victor 01.10.2025
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserTest() {
        User mockUser = new User("test", "aa@aa.aa", 33, LocalDate.now());
        userService.createUser(mockUser);
        Mockito.verify(userDao, Mockito.times(1)).create(mockUser);
    }

    @Test
    public void readUserTest() {
        User mockUser = new User("test", "aa@aa.aa", 33, LocalDate.now());
        when(userDao.read(1L)).thenReturn(mockUser);

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("test", result.getName());
        Mockito.verify(userDao, Mockito.times(1)).read(1L);
    }

    @Test
    public void readAllUsersTest() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("test", "aa@aa.aa", 33, LocalDate.now()));
        mockUsers.add(new User("test1", "aa@baa.aa", 43, LocalDate.now()));

        when(userDao.readAll()).thenReturn(mockUsers);
        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
        assertEquals("test", result.get(0).getName());
        assertEquals("test1", result.get(1).getName());
        Mockito.verify(userDao, Mockito.times(1)).readAll();
    }

    @Test
    public void updateUserTest() {
        User mockUser = new User("test", "aa@aa.aa", 33, LocalDate.now());
        userService.updateUser(mockUser);

        Mockito.verify(userDao, Mockito.times(1)).update(mockUser);
        Mockito.verify(userDao, never()).read(anyLong());
    }

    @Test
    public void deleteUserTest() {
        Long id = 2L;
        userService.deleteUser(id);
        Mockito.verify(userDao, Mockito.times(1)).delete(id);
    }
}
