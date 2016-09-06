package com.wp.finki.ukim.mk.catalogware.seeder;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.wp.finki.ukim.mk.catalogware.model.*;
import com.wp.finki.ukim.mk.catalogware.service.*;
import com.wp.finki.ukim.mk.catalogware.utils.ProductImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Borce on 26.08.2016.
 */
@Component
public class TableSeeder {

    private static final Logger logger = LoggerFactory.getLogger(TableSeeder.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductLikeService likeService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductImageUtils productImageUtils;

    private static final Random rand = new Random();
    private final Lorem lorem = LoremIpsum.getInstance();
    private List<User> admins = new ArrayList<>();

    @PostConstruct
    public void seed() {
        if (!areTablesSeeded()) {
            this.seedUsers();
            this.seedCategories();
            this.seedProducts();
            this.seedLikes();
            this.seedBasketProducts();
            this.seedOrders();
        }
    }

    private boolean areTablesSeeded() {
        return userService.count() > 0;
    }

    private void seedUsers() {
        logger.info("seeding users");
        User admin1 = new User("Admin", "admin@admin.com", "admin", new Date(), User.Role.ADMIN);
        User admin2 = new User("Borce Petrovski", "borce@petrovski.com", "password", new Date(), User.Role.ADMIN);
        User admin3 = new User("Kristijan Cvetkovic", "kristijan@cvetkovic.com", "password",
                new Date(), User.Role.ADMIN);
        admin1 = userService.store(admin1);
        admin2 = userService.store(admin2);
        admin3 = userService.store(admin3);
        userService.store(new User("John Doe", "john@doe.com", "password", new Date(), User.Role.CUSTOMER));
        userService.store(new User("User", "user@user.com", "password", new Date(), User.Role.CUSTOMER));
        for (int i = 0; i < 30; i++) {
            String name = lorem.getName();
            String email = generateEmail(name);
            userService.store(new User(name, email, "password", new Date(), User.Role.CUSTOMER));
        }
        admins.add(admin1);
        admins.add(admin2);
        admins.add(admin3);
        logger.info("users seeded");
    }

    private String generateEmail(String name) {
        String result = "";
        String[] parts = name.toLowerCase().split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (i == parts.length - 1) {
                result += parts[i];
            } else {
                result += parts[i] + ".";
            }
        }
        return result + "@user.com";
    }

    private void seedCategories() {
        logger.info("seeding categories");
        categoryService.store(new Category("Clothes"));
        categoryService.store(new Category("Jewelry"));
        categoryService.store(new Category("Shoes"));
        categoryService.store(new Category("Phones"));
        categoryService.store(new Category("Tablets"));
        categoryService.store(new Category("Desktop Computers"));
        categoryService.store(new Category("Laptops"));
        categoryService.store(new Category("Computer Parts"));
        categoryService.store(new Category("Sport"));
        categoryService.store(new Category("Watches"));
        categoryService.store(new Category("Toys"));
        categoryService.store(new Category("Security and Protection"));
        logger.info("categories seeded");
    }

    private void seedProducts() {
        logger.info("seeding products");
        final int priceMin = 10;
        final int priceMax = 10000;
        List<Category> categories = categoryService.getAll();
        for (int i = 0; i < 100; i++) {
            String name = lorem.getTitle(2, 5);
            String description = lorem.getParagraphs(2, 5);
            double price = priceMin + (priceMax - priceMin) * rand.nextDouble();
            byte[] image = productImageUtils.getBytes();
            Collections.shuffle(admins);
            User admin = admins.get(0);
            Product product = new Product(name, description, price, image, null, null, admin);
            int nCategories = 1 + rand.nextInt(2);
            product.setCategories(this.getSetNElements(categories, nCategories));
            productService.store(product);
        }
        logger.info("products seeded");
    }

    private void seedLikes() {
        logger.info("seeding likes");
        List<User> users = userService.getAll();
        List<Product> products = productService.getAll();
        for (int i = 0; i < 300;) {
            int randUser = rand.nextInt(users.size() - 1);
            int randProduct = rand.nextInt(products.size() - 1);
            ProductLikeId id = new ProductLikeId(users.get(randUser), products.get(randProduct));
            if (!likeService.exists(id)) {
                int rating = 1 + rand.nextInt(4);
                likeService.store(id, (short) rating);
                i++;
            }
        }
        logger.info("likes seeded");
    }

    private void seedBasketProducts() {
        List<Basket> baskets = basketService.getAll();
        List<Product> products = productService.getAll();
        logger.info("seeding baskets with products");
        for (int i = 0; i < baskets.size(); i++) {
            if (i % 4 != 4) {
                int nProducts = 2 + rand.nextInt(13);
                Basket basket = baskets.get(i);
                basketService.addProducts(basket.getId(), this.getListNElements(products, nProducts));
            }
        }
        logger.info("baskets seeded");
    }

    private void seedOrders() {
        List<User> users = userService.getAll();
        List<Product> products = productService.getAll();
        logger.info("seeding orders");
        for (int i = 0; i < 250; i++) {
            Collections.shuffle(users);
            Order order = new Order(null, null, lorem.getStateAbbr(), false, users.get(0));
            int nProducts = 2 + rand.nextInt(13);
            order.setProducts(getSetNElements(products, nProducts));
            orderService.store(order);
        }
        logger.info("orders seeded");
    }

    private <E> Set<E> getSetNElements(List<E> list, int n) {
        Collections.shuffle(list);
        Set<E> result = new HashSet<E>();
        for (int i = 0; i < n; i++) {
            if (i < list.size() - 1) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    private <E> List<E> getListNElements(List<E> list, int n) {
        Collections.shuffle(list);
        List<E> result = new ArrayList<E>();
        for (int i = 0; i < n; i++) {
            if (i < list.size() - 1) {
                result.add(list.get(i));
            }
        }
        return result;
    }
}
