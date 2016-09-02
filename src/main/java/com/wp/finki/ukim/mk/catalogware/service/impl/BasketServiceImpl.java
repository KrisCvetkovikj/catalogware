package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.repository.BasketRepository;
import com.wp.finki.ukim.mk.catalogware.service.BasketService;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository repository;

    @Autowired
    private ProductService productService;

    @Override
    public List<Basket> getAll() {
        return repository.findAll();
    }

    @Override
    public Basket get(long id) {
        return repository.getOne(id);
    }

    @Override
    public boolean exists(long id) {
        return this.get(id) != null;
    }

    @Override
    public boolean exists(Basket basket) {
        if (basket == null) {
            throw new IllegalArgumentException("basket can;t be null");
        }
        return this.exists(basket.getId());
    }

    @Override
    public Basket addProduct(long id, Product product) {
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't add product in basket, basket with id %d don't exists", id));
        }
        if (!productService.exists(product)) {
            throw new IllegalArgumentException(String
                    .format("can't add product in basket, product with id %d don't exists", product.getId()));
        }
        Basket basket = this.get(id);
        basket.getProducts().add(product);
        return repository.save(basket);
    }

    @Override
    public void addProducts(long id, List<Product> products) {
        for (Product product : products) {
            this.addProduct(id, product);
        }
    }
}
