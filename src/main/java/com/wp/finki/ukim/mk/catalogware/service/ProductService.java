package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
public interface ProductService {

    List<Product> getAll();

    List<Product> getAll(int page, int size, boolean latest);

    Product get(long id);

    boolean exists(long id);

    boolean exists(Product product);

    long count();

    Product store(Product product);

    Product store(Product product, MultipartFile image, AuthUser authUser);

    Product update(long id, Product product);

    Product update(long id, Product product, MultipartFile image);

    void delete(long id);

    long countBasketProducts(long basketId);

    List<Product> filterByCategories(String category);

    List<Product> filterByCategories(int page, int size, boolean latest, String category);

    List<Product> getOrderProducts(long orderId);
}
