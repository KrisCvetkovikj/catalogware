package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.User;

import java.util.List;

/**
 * Created by Borce on 26.08.2016.
 */
public interface UserService {

    List<User> getAll();

    User get(long id);

    User findByName(String name);

    User findByEmail(String email);

    boolean exists(long id);

    boolean exists(User user);

    long count();

    User store(User user);

    User store(String name, String email, String password);

    User store(String username, String email, String password, User.Role role);

    User update(long id, User user);

    boolean delete(long id);
}
