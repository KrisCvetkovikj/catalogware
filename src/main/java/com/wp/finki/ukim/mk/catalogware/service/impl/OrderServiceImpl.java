package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.repository.OrderRepository;
import com.wp.finki.ukim.mk.catalogware.service.OrderService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public Order get(long id) {
        return repository.findOne(id);
    }

    @Override
    public boolean exists(long id) {
        return this.get(id) != null;
    }

    @Override
    public boolean exists(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order can't be null");
        }
        return this.exists(order.getId());
    }

    @Override
    public long count() {
        return repository.count();
    }

    private void validateData(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order can't be null");
        }
        if (order.getUser() == null) {
            throw new IllegalArgumentException("order user can't be null");
        }
        if (order.getShippingAddress() == null) {
            throw new IllegalArgumentException("order shipping address can't be null");
        }
    }

    @Override
    public Order store(Order order) {
        this.validateData(order);
        if (userService.exists(order.getUser())) {
            throw new IllegalArgumentException(String
                    .format("can't save order, user with id %d don't exists", order.getUser().getId()));
        }
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setFinished(false);
        return repository.save(order);
    }

    @Override
    public Order store(User user, String shippingAddress) {
        return this.store(new Order(null, null, shippingAddress, false, user));
    }

    @Override
    public Order update(long id, Order order) {
        this.validateData(order);
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't update order, order with id %d don't exists", id));
        }
        order.setId(id);
        order.setUpdatedAt(new Date());
        return repository.save(order);
    }

    @Override
    public boolean delete(long id) {
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't delete order, order with id %d don;t exists", id));
        }
        repository.delete(id);
        return !this.exists(id);
    }
}
