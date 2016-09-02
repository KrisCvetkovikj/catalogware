package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.request.AuthenticateUserRequest;
import com.wp.finki.ukim.mk.catalogware.model.request.RegisterUserRequest;

/**
 * Created by Borce on 26.08.2016.
 */
public interface AuthService {

    String login(String email, String password);

    String register(String name, String email, String password);
}
