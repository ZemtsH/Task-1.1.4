package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Util util = new Util();
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30), lastName VARCHAR(30), age INT(2))");
        } catch (SQLException throwables) {
            System.out.println("Ошибка в создании таблицы");
        }
    }

    public void dropUsersTable() {
        Util util = null;
        try {
            util = new Util();
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate("DROP TABLE users");
        } catch (SQLException throwables) {
            System.out.println("Таблицы не существует!");
        }
    }

    private static final String INSERT = "INSERT INTO users VALUES (id,?,?,?)";

    public void saveUser(String name, String lastName, byte age) {
        Util util = null;
        PreparedStatement preparedStatement = null;
        try {
            util = new Util();
            Statement statement = util.getConnection().createStatement();
            preparedStatement = statement.getConnection().prepareStatement(INSERT);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static final String DELETE = "DELETE FROM users WHERE (id = ?)";

    public void removeUserById(long id) {
        Util util = null;
        PreparedStatement preparedStatement = null;
        try {
            util = new Util();
            Statement statement = util.getConnection().createStatement();
            preparedStatement = statement.getConnection().prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static final String GET_ALL = "SELECT * FROM users";

    public List<User> getAllUsers() {
        Util util = null;
        PreparedStatement preparedStatement = null;
        List<User> list = new LinkedList<User>();
        User user = new User();
        try {
            util = new Util();
            Statement statement = util.getConnection().createStatement();
            preparedStatement = statement.getConnection().prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                list.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list; // Тут придумать как вернуть лист
    }

    public void cleanUsersTable() {
        Util util = null;
        try {
            util = new Util();
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
