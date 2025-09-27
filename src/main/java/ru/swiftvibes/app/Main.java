package ru.swiftvibes.app;

import ru.swiftvibes.entity.User;
import ru.swiftvibes.service.UserService;
import ru.swiftvibes.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Victor 25.09.2025
 */

public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nWelcome!");
            System.out.println("\n-===Menu===-");
            System.out.println("1.add user");
            System.out.println("2.get user by ID");
            System.out.println("3.get all users");
            System.out.println("4.update user by ID");
            System.out.println("5.delete user by ID");
            System.out.println("6.erase all data");
            System.out.println("7.exit app");
            System.out.print("enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createUser();
                case 2 -> getUser();
                case 3 -> getAllUsers();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 6 -> eraseAll();
                case 7 -> {
                    isRunning = false;
                    HibernateUtil.closeSF();
                }
                default -> System.out.println("Invalid option");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void createUser() {
        System.out.println("enter your name: ");
        String name = scanner.nextLine();
        System.out.println("enter your email");
        String email = scanner.nextLine();
        System.out.println("enter your age");
        int age = Integer.parseInt(scanner.nextLine());

        User user = new User(name, email, age, LocalDate.now());
        userService.createUser(user);
        System.out.println("user " + user + " created successfully");
    }

    private static void getUser() {
        System.out.println("enter user id: ");
        Long id = Long.parseLong(scanner.nextLine());
        User user = userService.getUserById(id);
        System.out.println(user != null ? user.toString() : "user not found");
    }

    private static void getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("DB is empty");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void updateUser() {
        System.out.println("enter id to update: ");
        Long id = Long.parseLong(scanner.nextLine());
        User user = userService.getUserById(id);
        if (user == null) {
            System.out.println("user not found");
            return;
        }

        boolean isEditing = true;
        while (isEditing) {
            System.out.println("what would you like to edit?");
            System.out.println("choose action: ");
            System.out.println("1. only username");
            System.out.println("2. only email");
            System.out.println("3. only age");
            System.out.println("4. edit all");
            System.out.println("5. cancel/finish editing");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("enter new name: ");
                    user.setName(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("enter new email: ");
                    user.setEmail(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("enter new age: ");
                    user.setAge(Integer.parseInt(scanner.nextLine()));
                    break;
                case 4:
                    editAll(user);
                    break;
                case 5:
                    isEditing = false;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }
        userService.updateUser(user);
        System.out.println("User updated successfully: " + user);
    }

    private static void editAll(User user) {
        System.out.println("enter new name: ");
        user.setName(scanner.nextLine());
        System.out.println("enter new email: ");
        user.setEmail(scanner.nextLine());
        System.out.println("enter new age: ");
        user.setAge(Integer.parseInt(scanner.nextLine()));
    }

    private static void deleteUser() {
        System.out.println("enter id to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        userService.deleteUser(id);
        System.out.println("user " + id + " deleted successfully");
    }

    private static void eraseAll() {
        List<User> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            System.out.println("ARE YOU SURE YOU WANT TO ERASE ALL DATA?? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equals("y")) {
                userService.deleteAllUsers();
                System.out.println("DB has been erased");
            }
        } else {
            System.out.println("nothing to erase Db is empty");
        }
    }
}