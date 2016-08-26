package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.repository.UserRepository;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Borce on 26.08.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User get(long id) {
        return repository.findOne(id);
    }

    @Override
    public User findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User store(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user, long id) {
        //TODO
        return null;
    }

    @Override
    public void delete() {
        //TODO
    }
}
