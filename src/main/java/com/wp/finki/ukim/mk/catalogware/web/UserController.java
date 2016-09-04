package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.service.OrderService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Borce on 04.09.2016.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('ADMIN') or principal.id == #id")
    @GetMapping(value = "/{id}/orders")
    public List<Order> orders(@PathVariable long id,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size,
                              @RequestParam(required = false) Boolean latest) {
        page = (page != null) ? page : 0;
        size = (size != null) ? size : Integer.MAX_VALUE;
        latest = (latest != null) ? latest : true;
        return orderService.getUserOrders(id, page, size, latest);
    }
}
