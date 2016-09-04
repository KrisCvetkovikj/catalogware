package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.exception.BasketChangeFailedEException;
import com.wp.finki.ukim.mk.catalogware.exception.BasketNotFoundException;
import com.wp.finki.ukim.mk.catalogware.exception.EmptyBasketException;
import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.repository.BasketRepository;
import com.wp.finki.ukim.mk.catalogware.service.BasketService;
import com.wp.finki.ukim.mk.catalogware.service.OrderService;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 02.09.2016.
 */
@Service(value = "basketService")
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Basket> getAll() {
        return repository.findAll();
    }

    @Override
    public Basket get(long id) {
        return repository.findOne(id);
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
    public Basket addProduct(long id, long productId) {
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't add product in basket, basket with id %d don't exists", id));
        }
        if (!productService.exists(productId)) {
            throw new IllegalArgumentException(String
                    .format("can't add product in basket, product with id %d don't exists", productId));
        }
        Basket basket = this.get(id);
        basket.getProducts().add(new Product(productId));
        basket.setUpdatedAt(new Date());
        try {
            return repository.save(basket);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new BasketChangeFailedEException("error occurred while adding product in the basket");
        }
    }

    @Override
    public Basket addProduct(long id, Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can't be null");
        }
        return this.addProduct(id, product.getId());
    }

    @Override
    public Basket addProducts(long id, List<Product> products) {
        Basket basket = this.get(id);
        for (Product product : products) {
            basket.getProducts().add(product);
        }
        basket.setUpdatedAt(new Date());
        try {
            return repository.save(basket);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new BasketChangeFailedEException("error occurred while adding products in the basket");
        }
    }

    @Override
    public Basket removeProduct(long id, long productId) {
        Basket basket = repository.findOne(id);
        Iterator iterator = basket.getProducts().iterator();
        while (iterator.hasNext()) {
            Product product = (Product) iterator.next();
            if (product.getId().equals(productId)) {
                iterator.remove();
            }
        }
        basket.setUpdatedAt(new Date());
        try {
            return repository.save(basket);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new BasketChangeFailedEException("error occurred while removing product from the basket");
        }
    }

    @Override
    public boolean hasProduct(long id, long productId) {
        return repository.findByIdAndProductsId(id, productId) != null;
    }

    @Override
    public void checkout(User user, String shippingAddress) {
        if (user == null) {
            throw new IllegalArgumentException("user can't be null");
        }
        if (shippingAddress == null) {
            throw new IllegalArgumentException("shipping address can't be null");
        }
        Basket basket = this.get(user.getId());
        if (basket == null) {
            throw new BasketNotFoundException(String.format("can't find basket for user with id %d", user.getId()));
        }
        Set<Product> products = basket.getProducts();
        if (products.size() == 0) {
            throw new EmptyBasketException();
        }
        orderService.store(user, shippingAddress, products);
        basket.getProducts().clear();
        basket.setUpdatedAt(new Date());
        try {
            repository.save(basket);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new BasketChangeFailedEException("Error occurred while making checkout on the basket");
        }
    }
}
