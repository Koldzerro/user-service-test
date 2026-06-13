package org.example;

import org.example.entity.User;
import org.example.service.UserService;
import org.example.util.HibernateUtil;

import java.util.Scanner;

public class Main {

    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== User Service ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Создать пользователя");
            System.out.println("2. Найти пользователя по id");
            System.out.println("3. Показать всех пользователей");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: createUser(); break;
                case 2: getUser(); break;
                case 3: getAllUsers(); break;
                case 4: updateUser(); break;
                case 5: deleteUser(); break;
                case 0: running = false; break;
                default: System.out.println("Неверный выбор");
            }
        }

        HibernateUtil.shutdown();
        System.out.println("Программа завершена");
    }

    private static void createUser() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите возраст: ");
        Integer age = scanner.nextInt();
        scanner.nextLine();
        userService.createUser(name, email, age);
    }

    private static void getUser() {
        System.out.print("Введите id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        User user = userService.getUserById(id);
        if (user != null) {
            System.out.println(use1r);
        }
    }

    private static void getAllUsers() {
        userService.getAllUsers().forEach(System.out::println);
    }

    private static void updateUser() {
        System.out.print("Введите id пользователя: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите новый email: ");
        String email = scanner.nextLine();
        System.out.print("Введите новый возраст: ");
        Integer age = scanner.nextInt();
        scanner.nextLine();
        userService.updateUser(id, name, email, age);
    }

    private static void deleteUser() {
        System.out.print("Введите id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        userService.deleteUser(id);
    }
}