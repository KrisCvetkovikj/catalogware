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

    User store(User user);

    User update(User user, long id);

    void delete();
}
