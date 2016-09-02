package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.model.User;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
public interface OrderService {

    List<Order> getAll();

    Order get(long id);

    boolean exists(long id);

    boolean exists(Order order);

    long count();

    Order store(Order order);

    Order store(User user, String shippingAddress);

    Order update(long id, Order order);

    boolean delete(long id);
}
