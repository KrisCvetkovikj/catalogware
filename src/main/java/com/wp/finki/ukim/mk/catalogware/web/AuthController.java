package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.model.request.AuthenticateUserRequest;
import com.wp.finki.ukim.mk.catalogware.model.response.AuthenticateUserResponse;
import com.wp.finki.ukim.mk.catalogware.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Borce on 26.08.2016.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public AuthenticateUserResponse login(@RequestBody AuthenticateUserRequest request) {
        String token = authService.login(request);
        return new AuthenticateUserResponse(token);
    }
}