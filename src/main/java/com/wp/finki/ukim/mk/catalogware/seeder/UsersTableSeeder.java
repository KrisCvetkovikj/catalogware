package com.wp.finki.ukim.mk.catalogware.seeder;

import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Borce on 26.08.2016.
 */
@Component
public class UsersTableSeeder {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seed() {
        User user = new User(0L, "john-doe", "john@doe.com", passwordEncoder.encode("password"), User.Role.ADMIN);
        if (service.findByName(user.getName()) == null) {
            service.store(user);
        }
    }
}
