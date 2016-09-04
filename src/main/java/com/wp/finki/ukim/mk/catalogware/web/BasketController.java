package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.model.response.Response;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.service.BasketService;
import com.wp.finki.ukim.mk.catalogware.service.OrderService;
import com.wp.finki.ukim.mk.catalogware.utils.ValidationErrorsMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Borce on 03.09.2016.
 */
@RestController
@RequestMapping(value = "/basket")
public class BasketController extends BaseController {

    @Autowired
    private BasketService service;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping
    public Basket get(@AuthenticationPrincipal AuthUser authUser) {
        return service.get(authUser.getId());
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping(value = "/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public Response checkout(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Order order,
                             BindingResult bindingResult,
                             ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        super.checkForBadRequest(bindingResult, validationErrorsMessageConverter);
        service.checkout(authUser.getUser(), order.getShippingAddress());
        return new Response(201, "Order created", "The order was created successfully");
    }
}
