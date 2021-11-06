package com.nikita.model.dao.impl;

import com.nikita.model.dao.UserDao;
import com.nikita.model.dao.sqlConstants.SQLConstants;
import com.nikita.model.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {

    private Connection connection;

    public JDBCUserDao(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int create(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.CREATE_USER)){
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender());
            statement.setInt(6, user.getAge());
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<User> findByEmail(String email) {
        List<User> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.SHOW_USER_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            list = getUserList(resultSet);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.SHOW_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            list = getUserList(resultSet);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public int update(User user, String newEmail, String newPassword) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.UPDATE_USER_EMAIL)){
            statement.setString(1, newEmail);
            statement.setString(2, newPassword);
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.DELETE_USER_EMAIL)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteAll() {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.DELETE_ALL_USERS)){
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

    private List<User> getUserList(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setGender(resultSet.getString(6));
            user.setAge(Integer.parseInt(resultSet.getString(7)));
            result.add(user);
        }
        return result;
    }
}
