package org.example.dao;

import org.example.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.example.util.HibernateUtil;

import java.util.List;

@Testcontainers
public class UserDaoImplTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    private static SessionFactory sessionFactory;
    private UserDaoImpl userDao;

    @BeforeAll
    static void setUpClass() {
        sessionFactory = HibernateUtil.buildSessionFactory(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    void create_shouldSaveUserToDatabase() {

        User user = new User("Иван", "ivan@mail.ru", 25);

        userDao.create(user);

        assertNotNull(user.getId());
        User savedUser = userDao.getById(user.getId());
        assertNotNull(savedUser);
        assertEquals("Иван", savedUser.getName());
    }

    @Test
    void getById_shouldReturnUser_whenExists() {

        User user = new User("Мария", "maria@mail.ru", 28);
        userDao.create(user);

        User result = userDao.getById(user.getId());

        assertNotNull(result);
        assertEquals("Мария", result.getName());
        assertEquals("maria@mail.ru", result.getEmail());
    }

    @Test
    void getById_shouldReturnNull_whenNotExists() {

        User result = userDao.getById(999L);

        assertNull(result);
    }

    @Test
    void getAll_shouldReturnAllUsers() {

        userDao.create(new User("Иван", "ivan@mail.ru", 25));
        userDao.create(new User("Мария", "maria@mail.ru", 28));

        List<User> result = userDao.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void update_shouldUpdateUserInDatabase() {

        User user = new User("Иван", "ivan@mail.ru", 25);
        userDao.create(user);

        user.setName("Иван Петров");
        user.setAge(26);
        userDao.update(user);

        User updatedUser = userDao.getById(user.getId());
        assertEquals("Иван Петров", updatedUser.getName());
        assertEquals(26, updatedUser.getAge());
    }

    @Test
    void delete_shouldRemoveUserFromDatabase() {

        User user = new User("Иван", "ivan@mail.ru", 25);
        userDao.create(user);
        Long id = user.getId();

        userDao.delete(id);

        User result = userDao.getById(id);
        assertNull(result);
    }
}