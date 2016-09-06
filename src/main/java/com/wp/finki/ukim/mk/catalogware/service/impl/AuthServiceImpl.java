package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.exception.NotAuthenticatedException;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.service.AuthService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import com.wp.finki.ukim.mk.catalogware.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.security.Principal;

/**
 * Created by Borce on 26.08.2016.
 */
@Service(value = "authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Try to login a user with the given credentials.
     *
     * @param email    user email
     * @param password user password
     * @return json web token for the user
     */
    @Override
    public String login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationManager.authenticate(authenticationToken));
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUtils.generateToken(authUser);
    }

    /**
     * Store a new user with the given data.
     *
     * @param name     user name
     * @param email    user email
     * @param password user password
     * @return json web token for the user
     */
    @Override
    public String register(String name, String email, String password) {
        User storedUser = userService.store(name, email, password);
        if (storedUser == null) {
            throw new IllegalArgumentException("error occurred while saving the user");
        }
        return this.login(email, password);
    }

    public boolean isAuthenticated(AuthUser authUser) {
        if (authUser == null) {
            throw new NotAuthenticatedException();
        }
        return true;
    }
}
