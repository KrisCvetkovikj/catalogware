package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.exception.ResourceNotFoundException;
import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 04.09.2016.
 */
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<Order> index(@RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) Boolean latest) {
        page = (page != null) ? page : 0;
        size = (size != null) ? size : Integer.MAX_VALUE;
        latest = (latest != null) ? latest : true;
        return service.getAll(page, size, latest);
    }

    @PreAuthorize("hasAuthority('ADMIN') or @orderService.canSee(#id, principal.id)")
    @GetMapping(value = "/{id}")
    public Order get(@PathVariable long id) {
        Order order = service.get(id);
        if (order == null) {
            throw new ResourceNotFoundException();
        }
        return order;
    }

    @PreAuthorize("hasAuthority('ADMIN') or @orderService.canSee(#id, principal.id)")
    @GetMapping(value = "/{id}/products")
    public Set<Product> products(@PathVariable long id) {
        Order order = service.get(id);
        if (order == null) {
            throw new ResourceNotFoundException();
        }
        return order.getProducts();
    }
}
