package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
public interface BasketService {

    List<Basket> getAll();

    Basket get(long id);

    boolean exists(long id);

    boolean exists(Basket basket);

    Basket addProduct(long id, long productId);

    Basket addProduct(long id, Product product);

    Basket addProducts(long id, List<Product> products);

    Basket removeProduct(long id, long productId);

    boolean hasProduct(long id, long productId);

    void checkout(User user, String shippingAddress);
}
