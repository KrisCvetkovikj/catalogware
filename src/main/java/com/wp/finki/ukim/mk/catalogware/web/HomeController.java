package com.wp.finki.ukim.mk.catalogware.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Borce on 26.08.2016.
 */
@RestController
public class HomeController {

    @GetMapping(value = "/")
    public String home() {
        return "Hello World";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/me")
    public String me() {
        return "Only authenticated";
    }
}
