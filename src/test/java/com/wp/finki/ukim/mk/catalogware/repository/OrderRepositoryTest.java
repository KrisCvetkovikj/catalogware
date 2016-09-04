package com.wp.finki.ukim.mk.catalogware.repository;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Order;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.utils.ProductImageUtils;
import com.wp.finki.ukim.mk.catalogware.utils.SetUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Borce on 01.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogwareApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ProductImageUtils productImageUtils;

    private User user1 = new User(1, "User 1", "user@user.com", "pass", new Date(), User.Role.CUSTOMER);
    private User user2 = new User(2, "User 2", "user-2@user.com", "pass", new Date(), User.Role.CUSTOMER);
    private Product product1 = new Product(1, "Product 1", "Temp product 1", 123, null, new Date(), new Date(), user1);
    private Product product2 = new Product(2, "Product 2", "Temp product 2", 123, null, new Date(), new Date(), user2);
    private Product product3 = new Product(3, "Product 3", "Temp product 3", 123, null, new Date(), new Date(), user1);

    private final int NUMBER_OF_ORDERS = 3;
    private final long order1Id = 1;
    private Order order1 = new Order(order1Id, new Date(), new Date(), "Address 1", false, user1);
    private final long order2Id = 2;
    private Order order2 = new Order(order2Id, new Date(), new Date(), "Address 2", true, user2);
    private final long order3Id = 3;
    private Order order3 = new Order(order3Id, new Date(), new Date(), "Address 3", false, user1);
    private final long unExistingOrderId = 4;
    private Order unExistingOrder = new Order(unExistingOrderId, new Date(), new Date(), "Address 4", true, user2);

    @Before
    public void setup() {
        byte[] image = productImageUtils.getBytes();
        product1.setImage(image);
        product2.setImage(image);
        product3.setImage(image);
        Set<Product> products1 = new HashSet<>();
        products1.add(product1);
        products1.add(product2);
        Set<Product> products2 = new HashSet<>();
        products2.add(product2);
        products2.add(product3);
        Set<Product> products3 = new HashSet<>();
        products3.add(product1);
        products3.add(product3);

        order1.setProducts(products1);
        order2.setProducts(products2);
        order3.setProducts(products3);
        unExistingOrder.setProducts(products2);

        userRepository.save(user1);
        userRepository.save(user2);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        repository.save(order1);
        repository.save(order2);
        repository.save(order3);
    }

    @After
    public void shutdown() {
        repository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Order.class);
        productRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Product.class);
        userRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, User.class);
    }

    /**
     * Assert that the given order data is same with the actual order.
     *
     * @param actualOrder actual order
     * @param order       order to be checked
     */
    private void assertOrder(Order actualOrder, Order order) {
        assertEquals(actualOrder, order);
        assertEquals(actualOrder.getUser(), order.getUser());
        assertTrue(SetUtils.equals(actualOrder.getProducts(), order.getProducts()));
    }

    /**
     * Temp that findAll will return all orders in the database.
     */
    @Test
    public void testFindAll() {
        List<Order> orders = repository.findAll();
        assertEquals(NUMBER_OF_ORDERS, orders.size());
        int counter = 0;
        for (Order order : orders) {
            if (order.getId().equals(order1Id)) {
                this.assertOrder(order1, order);
                counter++;
            } else if (order.getId().equals(order2Id)) {
                this.assertOrder(order2, order);
                counter++;
            } else if (order.getId().equals(order3Id)) {
                this.assertOrder(order3, order);
                counter++;
            }
        }
        assertEquals(NUMBER_OF_ORDERS, counter);
    }

    /**
     * Temp that findOne will return the order when the id exists in the database.
     */
    @Test
    public void testFindOne() {
        Order order = repository.findOne(order1Id);
        this.assertOrder(order1, order);
    }

    /**
     * Temp that findOne will return null when the id don't exists.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        assertNull(repository.findOne(unExistingOrderId));
    }

    /**
     * Temp that count will return the number of orders in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_ORDERS, repository.count());
    }

    /**
     * Temp that store will create a new order in the database.
     */
    @Test
    public void testStore() {
        Order order = repository.save(unExistingOrder);
        assertEquals(NUMBER_OF_ORDERS + 1, repository.count());
        this.assertOrder(unExistingOrder, order);
        Order savedOrder = repository.findOne(unExistingOrderId);
        this.assertOrder(unExistingOrder, savedOrder);
    }

    /**
     * Temp that save will update the order data when the order id exists.
     */
    @Test
    public void testUpdate() {
        order2.setShippingAddress("Updated Address");
        order2.getProducts().add(product1);
        Order order = repository.save(order2);
        assertEquals(NUMBER_OF_ORDERS, repository.count());
        this.assertOrder(order2, order);
        Order updatedOrder = repository.findOne(order2Id);
        this.assertOrder(order2, updatedOrder);
    }

    /**
     * Temp that delete will remove the order data from the database.
     */
    @Test
    public void testDelete() {
        repository.delete(order3Id);
        assertEquals(NUMBER_OF_ORDERS - 1, repository.count());
        assertNull(repository.findOne(order3Id));
    }

    @Test
    public void testFindUserId() {
        List<Order> orders = repository.findByUserId(user1.getId());
        assertEquals(2, orders.size());
        assertEquals(order1, orders.get(0));
        assertEquals(order3, orders.get(1));
    }
}
