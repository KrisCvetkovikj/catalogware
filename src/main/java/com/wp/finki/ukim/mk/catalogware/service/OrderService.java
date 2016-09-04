package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 02.09.2016.
 */
public interface OrderService {

    List<Order> getAll();

    List<Order> getAll(int page, int size, boolean latest);

    List<Order> getUserOrders(long userId, int page, int size, boolean latest);

    Order get(long id);

    boolean exists(long id);

    boolean exists(Order order);

    long count();

    Order store(Order order);

    Order store(User user, String shippingAddress, Set<Product> products);

    Order update(long id, Order order);

    void delete(long id);

    boolean canSee(long id, long userId);
}
