package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class UserServiceTest {

        @Mock
        private UserDao userDao;

        private UserService userService;

        @BeforeEach
        void setUp() {
            userService = new UserService(userDao);
        }

        @Test
        void createUser_shouldCallDaoCreate() {

            String name = "Иван";
            String email = "ivan@mail.ru";
            Integer age = 25;

            userService.createUser(name, email, age);

            verify(userDao, times(1)).create(any(User.class));
        }

        @Test
        void getUserById_shouldReturnUser_whenExists() {

            Long id = 1L;
            User expectedUser = new User("Иван", "ivan@mail.ru", 25);
            when(userDao.getById(id)).thenReturn(expectedUser);

            User result = userService.getUserById(id);

            assertNotNull(result);
            assertEquals("Иван", result.getName());
            verify(userDao, times(1)).getById(id);
        }

        @Test
        void getUserById_shouldReturnNull_whenNotExists() {

            Long id = 999L;
            when(userDao.getById(id)).thenReturn(null);

            User result = userService.getUserById(id);

            assertNull(result);
            verify(userDao, times(1)).getById(id);
        }

        @Test
        void getAllUsers_shouldReturnListOfUsers() {

            User user1 = new User("Иван", "ivan@mail.ru", 25);
            User user2 = new User("Мария", "maria@mail.ru", 30);
            List<User> expectedUsers = Arrays.asList(user1, user2);
            when(userDao.getAll()).thenReturn(expectedUsers);

            List<User> result = userService.getAllUsers();

            assertEquals(2, result.size());
            verify(userDao, times(1)).getAll();
        }

        @Test
        void updateUser_shouldUpdate_whenUserExists() {

            Long id = 1L;
            User existingUser = new User("Иван", "ivan@mail.ru", 25);
            when(userDao.getById(id)).thenReturn(existingUser);

            userService.updateUser(id, "Иван Петров", "petrov@mail.ru", 26);

            verify(userDao, times(1)).update(existingUser);
            assertEquals("Иван Петров", existingUser.getName());
            assertEquals("petrov@mail.ru", existingUser.getEmail());
        }

        @Test
        void updateUser_shouldNotCallUpdate_whenUserNotExists() {
            Long id = 999L;
            when(userDao.getById(id)).thenReturn(null);

            userService.updateUser(id, "Имя", "email@mail.ru", 20);

            verify(userDao, never()).update(any(User.class));
        }

        @Test
        void deleteUser_shouldCallDaoDelete() {
            Long id = 1L;

            userService.deleteUser(id);

            verify(userDao, times(1)).delete(id);
        }
    }
