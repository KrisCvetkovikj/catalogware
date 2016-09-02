package com.wp.finki.ukim.mk.catalogware.seeder;

import com.wp.finki.ukim.mk.catalogware.model.Category;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.service.CategoryService;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Borce on 26.08.2016.
 */
//@Component
public class TableSeeder {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void seed() {
        User admin1 = new User("Borce Petrovski", "borce@petrovski.com", "password", new Date(), User.Role.ADMIN);
        User admin2 = new User("Kristijan Cvetkovic", "kristijan@cvetkovic.com", "password",
                new Date(), User.Role.ADMIN);
        User user1 = new User("John Doe", "john@doe.com", "password", new Date(), User.Role.CUSTOMER);
        User user2 = new User("User", "user@user.com", "password", new Date(), User.Role.CUSTOMER);
        User user3 = new User("Unknown User", "unknown@user.com", "password", new Date(), User.Role.CUSTOMER);
        admin1 = userService.store(admin1);
        admin2 = userService.store(admin2);
        user1 = userService.store(user1);
        user2 = userService.store(user2);
        user3 = userService.store(user3);

        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");
        Category category3 = new Category("Category 3");
        Category category4 = new Category("Category 4");
        category1 = categoryService.store(category1);
        category2 = categoryService.store(category2);
        category3 = categoryService.store(category3);
        category4 = categoryService.store(category4);

        Product product1 = new Product("Product 1", "Test product 1", 123, null, new Date(), new Date(), admin1);
        Product product2 = new Product("Product 2", "Test product 2", 123, null, new Date(), new Date(), admin2);
        Product product3 = new Product("Product 3", "Test product 3", 124, null, new Date(), new Date(), admin1);
        Product product4 = new Product("Product 4", "Test product 4", 128, null, new Date(), new Date(), admin2);
        Product product5 = new Product("Product 5", "Test product 5", 124, null, new Date(), new Date(), admin1);

        product1 = productService.store(product1);
        product2 = productService.store(product2);
        product3 = productService.store(product3);
        product4 = productService.store(product4);
        product5 = productService.store(product5);
    }
}
