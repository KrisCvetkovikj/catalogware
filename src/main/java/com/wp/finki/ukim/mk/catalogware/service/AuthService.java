package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.request.AuthenticateUserRequest;

/**
 * Created by Borce on 26.08.2016.
 */
public interface AuthService {

    String login(AuthenticateUserRequest request);
}
