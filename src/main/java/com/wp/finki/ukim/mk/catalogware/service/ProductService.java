package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
public interface ProductService {

    List<Product> getAll();

    Product get(long id);

    boolean exists(long id);

    boolean exists(Product product);

    long count();

    Product store(Product product);

    Product store(String name, String description, double price, byte[] image, User admin);

    Product update(long id, Product product);

    boolean delete(long id);
}
