package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.entity.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String name, String email, Integer age) {
        User user = new User(name, email, age);
        userDao.create(user);
        System.out.println("Пользователь создан: " + user);
    }

    public User getUserById(Long id) {
        User user = userDao.getById(id);
        if (user == null) {
            System.out.println("Пользователь с id=" + id + " не найден");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void updateUser(Long id, String name, String email, Integer age) {
        User user = userDao.getById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.update(user);
            System.out.println("Пользователь обновлён: " + user);
        } else {
            System.out.println("Пользователь с id=" + id + " не найден");
        }
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
        System.out.println("Пользователь с id=" + id + " удалён");
    }
}