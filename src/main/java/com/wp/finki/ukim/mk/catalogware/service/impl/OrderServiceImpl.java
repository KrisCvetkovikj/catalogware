package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.exception.OrderChangeFailedException;
import com.wp.finki.ukim.mk.catalogware.exception.OrderNotFoundException;
import com.wp.finki.ukim.mk.catalogware.exception.UserNotFoundException;
import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.repository.OrderRepository;
import com.wp.finki.ukim.mk.catalogware.service.OrderService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 02.09.2016.
 */
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    private PageRequest cretePageRequest(int page, int size, boolean latest) {
        if (latest) {
            return new PageRequest(page, size, Sort.Direction.DESC, "updatedAt");
        } else {
            return new PageRequest(page, size);
        }
    }

    @Override
    public List<Order> getAll(int page, int size, boolean latest) {
        return repository.findAll(cretePageRequest(page, size, latest)).getContent();
    }

    @Override
    public List<Order> getUserOrders(long userId, int page, int size, boolean latest) {
        return repository.findByUserId(userId, cretePageRequest(page, size, latest));
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
        if (order.getProducts() == null) {
            throw new IllegalArgumentException("order products can't be null");
        }
        if (order.getProducts().size() == 0) {
            throw new IllegalArgumentException("order must contains at least one product");
        }
    }

    @Override
    public Order store(Order order) {
        this.validateData(order);
        if (!userService.exists(order.getUser())) {
            throw new UserNotFoundException(String
                    .format("can't save order, user with id %d don't exists", order.getUser().getId()));
        }
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setFinished(false);
        try {
            return repository.save(order);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new OrderChangeFailedException("");
        }
    }

    @Override
    public Order store(User user, String shippingAddress, Set<Product> products) {
        return this.store(new Order(0, null, null, shippingAddress, false, user, products));
    }

    @Override
    public Order update(long id, Order order) {
        this.validateData(order);
        if (!this.exists(id)) {
            throw new OrderNotFoundException(String
                    .format("can't update order, order with id %d don't exists", id));
        }
        order.setId(id);
        order.setUpdatedAt(new Date());
        try {
            return repository.save(order);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new OrderChangeFailedException("error occurred while updating the order");
        }
    }

    @Override
    public void delete(long id) {
        if (!this.exists(id)) {
            throw new OrderNotFoundException(String
                    .format("can't delete order, order with id %d don;t exists", id));
        }
        try {
            repository.delete(id);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new OrderChangeFailedException("error occurred while deleting the order");
        }
    }

    @Override
    public boolean canSee(long id, long userId) {
        Order order = this.get(id);
        return order != null && order.getUser() != null && order.getUser().getId().equals(userId);
    }
}
