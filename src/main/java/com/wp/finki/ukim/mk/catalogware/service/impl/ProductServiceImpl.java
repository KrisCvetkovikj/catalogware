package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.exception.ProductChangeFailedException;
import com.wp.finki.ukim.mk.catalogware.exception.ProductNotFoundException;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.repository.ProductRepository;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private UserService userService;

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    private PageRequest getPageRequest(int page, int size, boolean latest) {
        if (latest) {
            return new PageRequest(page, size, Sort.Direction.DESC, "updatedAt");
        } else {
            return new PageRequest(page, size);
        }
    }

    @Override
    public List<Product> getAll(int page, int size, boolean latest) {
        return repository.findAll(getPageRequest(page, size, latest)).getContent();
    }

    @Override
    public Product get(long id) {
        return repository.findOne(id);
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
        try {
            return repository.save(product);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ProductChangeFailedException("Error occurred while saving the product");
        }
    }

    @Override
    public Product store(String name, String description, double price, MultipartFile image, AuthUser authUser) {
        if (image == null) {
            throw new IllegalArgumentException("image can't be null");
        }
        byte[] imageBytes = null;
        try {
            imageBytes = image.getBytes();
            User admin = authUser.getUser();
            return this.store(new Product(name, description, price, imageBytes, null, null, admin));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProductChangeFailedException("Error occurred while saving the product");
        }
    }

    @Override
    public Product update(long id, Product product) {
        this.validateData(product);
        if (!this.exists(id)) {
            throw new ProductNotFoundException(String.format("product with id %d don't exists", id));
        }
        product.setId(id);
        product.setUpdatedAt(new Date());
        try {
            return repository.save(product);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ProductChangeFailedException("Error occurred while updating the product");
        }
    }

    @Override
    public Product update(long id, String name, String description, double price, MultipartFile image) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("description can't be null");
        }
        if (image == null) {
            throw new IllegalArgumentException("image can't be null");
        }
        Product product = this.get(id);
        if (product == null) {
            throw new ProductNotFoundException(String
                    .format("can't update product, product with id %d don't exists", id));
        }
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setUpdatedAt(new Date());
        try {
            product.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProductChangeFailedException("Error occurred while updating the product");
        }
        try {
            return repository.save(product);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ProductChangeFailedException("Error occurred while updating the product");
        }
    }

    @Override
    public void delete(long id) {
        if (!this.exists(id)) {
            throw new ProductNotFoundException(String
                    .format("can't delete product, product with id %d don't exists", id));
        }
        try {
            repository.delete(id);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ProductChangeFailedException("Error occurred while deleting the product");
        }
    }

    @Override
    public long countBasketProducts(long basketId) {
        return repository.countByBasketsId(basketId);
    }

    @Override
    public List<Product> filterByCategories(String category) {
        String[] categories = category.split(",");
        return repository.findDistinctByCategoriesNameIn(categories);
    }

    @Override
    public List<Product> filterByCategories(int page, int size, boolean latest, String category) {
        String[] categories = category.split(",");
        return repository.findDistinctByCategoriesNameIn(getPageRequest(page, size, latest), categories);
    }

    @Override
    public List<Product> getOrderProducts(long orderId) {
        return repository.findByOrdersId(orderId);
    }
}
