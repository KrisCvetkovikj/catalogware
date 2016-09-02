package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.repository.UserRepository;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Borce on 26.08.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public boolean exists(long id) {
        return this.get(id) != null;
    }

    @Override
    public boolean exists(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user can;t be null");
        }
        return this.exists(user.getId());
    }

    @Override
    public long count() {
        return repository.count();
    }

    private void validateData(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user can't be null");
        }
        if (user.getName() == null) {
            throw new IllegalArgumentException("user name can;t be null");
        }
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("user email can;t be null");
        }
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("user password can't be null");
        }
    }

    @Override
    public User store(User user) {
        this.validateData(user);
        if (this.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException(String
                    .format("can't save user, user with email %s already exists", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        return repository.save(user);
    }

    @Override
    public User store(String name, String email, String password) {
        User user = new User(name, email, password, null, User.Role.CUSTOMER);
        return this.store(user);
    }

    @Override
    public User store(String username, String email, String password, User.Role role) {
        User user = new User(username, email, password, null, role);
        return this.store(user);
    }

    @Override
    public User update(long id, User user) {
        this.validateData(user);
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String.format("can't update user, user with id %d don't exists", id));
        }
        user.setId(id);
        return repository.save(user);
    }

    @Override
    public boolean delete(long id) {
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't delete user, user with id %d don't exists", id));
        }
        repository.delete(id);
        return !this.exists(id);
    }
}
