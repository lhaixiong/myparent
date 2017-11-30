package com.lhx.mybaits.dao;

import com.lhx.mybaits.entity.User;

import java.util.List;

public interface UserDao {
    public void insert(User user);

    public User findUserById (int userId);

    public List<User> findAllUsers();
}
