package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Product;

import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * Created by Borce on 02.09.2016.
 */
public interface BasketService {

    List<Basket> getAll();

    Basket get(long id);

    boolean exists(long id);

    boolean exists(Basket basket);

    Basket addProduct(long id, Product product);

    void addProducts(long id, List<Product> products);
}
