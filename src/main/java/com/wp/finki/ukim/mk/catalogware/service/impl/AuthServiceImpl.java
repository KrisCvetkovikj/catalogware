package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.model.json.request.AuthenticateUserRequest;
import com.wp.finki.ukim.mk.catalogware.model.json.response.AuthenticateUserResponse;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.service.AuthService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import com.wp.finki.ukim.mk.catalogware.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Borce on 26.08.2016.
 */
@Service
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
     * @param request request holder class for the user credentials
     * @return json web token for the user
     */
    @Override
    public String login(AuthenticateUserRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationManager.authenticate(authenticationToken));
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUtils.generateToken(authUser);
    }
}
