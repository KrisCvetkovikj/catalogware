package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.exception.ProductChangeFailedException;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.repository.ProductRepository;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private UserService userService;

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product get(long id) {
        return repository.getOne(id);
    }

    @Override
    public boolean exists(long id) {
        return this.get(id) != null;
    }

    @Override
    public boolean exists(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can't be null");
        }
        return this.exists(product.getId());
    }

    @Override
    public long count() {
        return repository.count();
    }

    private void validateData(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can;t be null");
        }
        if (product.getName() == null) {
            throw new IllegalArgumentException("product name can't be null");
        }
        if (product.getDescription() == null) {
            throw new IllegalArgumentException("product description can't be null");
        }
        if (product.getAdmin() == null) {
            throw new IllegalArgumentException("product admin can;t be null");
        }
    }

    @Override
    public Product store(Product product) {
        this.validateData(product);
        if (!userService.exists(product.getAdmin())) {
            throw new IllegalArgumentException(String
                    .format("can't save product, admin with id %d don't exists", product.getAdmin().getId()));
        }
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        Product result = repository.save(product);
        if (result == null) {
            throw new ProductChangeFailedException("Error occurred while saving the product");
        }
        return result;
    }

    @Override
    public Product store(String name, String description, double price, byte[] image, User admin) {
        return this.store(new Product(name, description, price, image, null, null, admin));
    }

    @Override
    public Product update(long id, Product product) {
        this.validateData(product);
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't update product, product with id %d don't exists", id));
        }
        product.setId(id);
        product.setUpdatedAt(new Date());
        Product result = repository.save(product);
        if (result == null) {
            throw new ProductChangeFailedException("Error occurred while updating the product");
        }
        return result;
    }

    @Override
    public boolean delete(long id) {
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't delete product, product with id %d don't exists", id));
        }
        repository.delete(id);
        boolean deleted = !this.exists(id);
        if (!deleted) {
            throw new ProductChangeFailedException("Error occurred while deleting the product");
        }
        return true;
    }
}
