package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
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

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Borce on 01.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogwareApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class BasketRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository repository;

    @Autowired
    private ApplicationContext context;

    private User admin = new User(1, "Admin", "admin@admin.admin", "admin", new Date(), User.Role.ADMIN);
    private User user1 = new User(2, "User 1", "user-1@user", "pass", new Date(), User.Role.CUSTOMER);
    private User user2 = new User(3, "User 2", "user-2@user", "pass", new Date(), User.Role.CUSTOMER);
    private User unExistingUser = new User(4, "User 3", "user-3@user", "pass", new Date(), User.Role.CUSTOMER);
    private Product product1 = new Product(1, "Product 1", "Test product 1", 123, null,
            new Date(), new Date(), admin);
    private Product product2 = new Product(2, "Product 2", "Test product 2", 123, null,
            new Date(), new Date(), admin);
    private Product product3 = new Product(3, "Product 3", "Test product 3", 123, null,
            new Date(), new Date(), admin);

    private final int NUMBER_OF_BASKETS = 2;
    private Basket basket1 = new Basket(user1.getId(), new Date(), user1);
    private Basket basket2 = new Basket(user2.getId(), new Date(), user2);
    private Basket unExistingBasket = new Basket(unExistingUser.getId(), new Date(), unExistingUser);

    @Before
    public void setup() {
        Set<Product> products1 = new HashSet<>();
        products1.add(product1);
        products1.add(product2);
        Set<Product> products2 = new HashSet<>();
        products2.add(product2);
        products2.add(product3);
        basket1.setProducts(products1);
        basket2.setProducts(products2);
        unExistingBasket.setProducts(products1);

        user1.setBasket(basket1);
        user2.setBasket(basket2);
        unExistingUser.setBasket(unExistingBasket);

        userRepository.save(admin);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @After
    public void shutdown() {
        userRepository.delete(user1.getId());
        userRepository.delete(user2.getId());
        productRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Product.class);
        userRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, User.class);
    }

    /**
     * Assert that the given basket data is same as the actual basket.
     *
     * @param actualBasket actual basket
     * @param basket       basket to be checked
     */
    private void assertBasket(Basket actualBasket, Basket basket) {
        assertEquals(actualBasket, basket);
        assertEquals(actualBasket.getUser(), basket.getUser());
        assertTrue(SetUtils.equals(actualBasket.getProducts(), basket.getProducts()));
    }

    /**
     * Test that findAll will return all baskets in the database.
     */
    @Test
    public void testFindAll() {
        List<Basket> baskets = repository.findAll();
        assertEquals(NUMBER_OF_BASKETS, baskets.size());
        int counter = 0;
        for (Basket basket : baskets) {
            if (basket.getId().equals(basket1.getId())) {
                this.assertBasket(basket1, basket);
                counter++;
            } else if (basket.getId().equals(basket2.getId())) {
                this.assertBasket(basket2, basket);
                counter++;
            }
        }
        assertEquals(NUMBER_OF_BASKETS, counter);
    }

    /**
     * Test that findOne will return the basket when the id exists in the database.
     */
    @Test
    public void testFindOne() {
        Basket basket = repository.findOne(basket1.getId());
        this.assertBasket(basket1, basket);
    }

    /**
     * Test that findOne will return null when the id don't exists.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        assertNull(repository.findOne(unExistingBasket.getId()));
    }

    /**
     * Test that count will return the number of baskets in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_BASKETS, repository.count());
    }

    /**
     * Test that save method will save a new basket in the database when the id don't exists.
     */
    @Test
    public void testStore() {
        userRepository.save(unExistingUser);
        assertEquals(NUMBER_OF_BASKETS + 1, repository.count());
        Basket savedBasket = repository.findOne(unExistingBasket.getId());
        this.assertBasket(unExistingBasket, savedBasket);
        userRepository.delete(unExistingUser);
    }

    /**
     * Test that save method will update basket data when the id exists in the database.
     */
    @Test
    public void testUpdate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        basket1.setUpdatedAt(calendar.getTime());
        Basket basket = repository.save(basket1);
        assertEquals(NUMBER_OF_BASKETS, repository.count());
        this.assertBasket(basket1, basket);
        Basket updatedBasket = repository.findOne(basket1.getId());
        this.assertBasket(basket1, updatedBasket);
    }

    /**
     * Test that delete will remove the basket data from the database.
     */
    @Test
    public void testDelete() {
        userRepository.save(unExistingUser);
        assertEquals(NUMBER_OF_BASKETS + 1, repository.count());
        userRepository.delete(unExistingUser);
        assertEquals(NUMBER_OF_BASKETS, repository.count());
        assertNull(repository.findOne(unExistingBasket.getId()));
    }
}
