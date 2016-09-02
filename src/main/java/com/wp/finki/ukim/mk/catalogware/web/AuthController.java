package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.model.request.AuthenticateUserRequest;
import com.wp.finki.ukim.mk.catalogware.model.request.RegisterUserRequest;
import com.wp.finki.ukim.mk.catalogware.model.response.AuthenticateUserResponse;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Borce on 26.08.2016.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/login")
    public AuthenticateUserResponse login(@RequestBody AuthenticateUserRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new AuthenticateUserResponse(token, authUser.getName(), authUser.getRole());
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/register")
    public AuthenticateUserResponse register(@RequestBody RegisterUserRequest request) {
        String token = authService.register(request.getName(), request.getEmail(), request.getPassword());
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new AuthenticateUserResponse(token, authUser.getName(), authUser.getRole());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public Map<String, Object> user(AuthUser user) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", user.getName());
        result.put("role", user.getRole());
        return result;
    }
}