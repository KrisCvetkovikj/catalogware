package com.wp.finki.ukim.mk.catalogware.model.security;

import com.wp.finki.ukim.mk.catalogware.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by Borce on 26.08.2016.
 */
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public User.Role getRole() {
        return user.getRole();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getName() {
        return user.getName();
    }
}
