package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        System.out.println("Таблица пользователей создана.");

        userService.saveUser("John", "Doe", (byte) 30);
        System.out.println("User с именем John добавлен в базу данных.");
        userService.saveUser("Alice", "Smith", (byte) 25);
        System.out.println("User с именем Alice добавлен в базу данных.");
        userService.saveUser("Bob", "Johnson", (byte) 40);
        System.out.println("User с именем Bob добавлен в базу данных.");
        userService.saveUser("Emily", "Brown", (byte) 35);
        System.out.println("User с именем Emily добавлен в базу данных.");

        List<User> users = userService.getAllUsers();
        System.out.println("Список пользователей:");
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        System.out.println("Таблица пользователей очищена.");

        userService.dropUsersTable();
        System.out.println("Таблица пользователей удалена.");
    }
}
