package com.nikita.model.service;

import com.nikita.model.dao.UserDao;
import com.nikita.model.dao.impl.JDBCUserDao;
import com.nikita.model.entity.User;

import java.sql.Connection;
import java.util.List;

public class UserServiceImpl implements UserService{

    private final UserDao dao;

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    public int createUser(User user) {
            return dao.create(user);
    }

    public int updateUser(User user, String newEmail, String newPassword) {
            return dao.update(user, newEmail, newPassword);
    }

    public int deleteUser(User user) {
            return dao.delete(user);
    }

    public int deleteAllUsers() {
            return dao.deleteAll();
    }

    public List<User> findUserByEmail(String email) {
            return dao.findByEmail(email);
    }

    public List<User> findAllUsers() {
            return dao.findAll();
    }
}
