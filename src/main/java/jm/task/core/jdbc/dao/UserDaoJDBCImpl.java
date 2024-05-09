package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl(Connection connection) {
        Util util = new Util();
        this.connection = util.getConnection();
    }

    private final Connection connection;

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastName VARCHAR(45) NOT NULL," +
                    "age TINYINT NOT NULL," +
                    "PRIMARY KEY (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Добавлен этот вывод для проверки
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Users retrieved from database: " + users); // Добавлен этот вывод для проверки
        return users;
    }


    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
