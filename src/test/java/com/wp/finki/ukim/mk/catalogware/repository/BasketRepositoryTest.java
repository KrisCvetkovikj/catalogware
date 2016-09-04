package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.utils.ProductImageUtils;
import com.wp.finki.ukim.mk.catalogware.utils.SetUtils;
import org.hibernate.validator.cfg.defs.AssertTrueDef;
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

import static org.junit.Assert.*;

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

    @Autowired
    private ProductImageUtils productImageUtils;

    private User admin = new User(1, "Admin", "admin@admin.admin", "admin", new Date(), User.Role.ADMIN);
    private User user1 = new User(2, "User 1", "user-1@user", "pass", new Date(), User.Role.CUSTOMER);
    private User user2 = new User(3, "User 2", "user-2@user", "pass", new Date(), User.Role.CUSTOMER);
    private User unExistingUser = new User(4, "User 3", "user-3@user", "pass", new Date(), User.Role.CUSTOMER);
    private Product product1 = new Product(1, "Product 1", "Temp product 1", 123, null,
            new Date(), new Date(), admin);
    private Product product2 = new Product(2, "Product 2", "Temp product 2", 123, null,
            new Date(), new Date(), admin);
    private Product product3 = new Product(3, "Product 3", "Temp product 3", 123, null,
            new Date(), new Date(), admin);

    private final int NUMBER_OF_BASKETS = 2;
    private Basket basket1 = new Basket(user1.getId(), new Date(), user1);
    private Basket basket2 = new Basket(user2.getId(), new Date(), user2);
    private Basket unExistingBasket = new Basket(unExistingUser.getId(), new Date(), unExistingUser);

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
    }

    /**
     * Temp that findAll will return all baskets in the database.
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
     * Temp that findOne will return the basket when the id exists in the database.
     */
    @Test
    public void testFindOne() {
        Basket basket = repository.findOne(basket1.getId());
        this.assertBasket(basket1, basket);
    }

    /**
     * Temp that findOne will return null when the id don't exists.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        assertNull(repository.findOne(unExistingBasket.getId()));
    }

    /**
     * Temp that count will return the number of baskets in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_BASKETS, repository.count());
    }

    /**
     * Temp that save method will save a new basket in the database when the id don't exists.
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
     * Temp that save method will update basket data when the id exists in the database.
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
     * Temp that delete will remove the basket data from the database.
     */
    @Test
    public void testDelete() {
        userRepository.save(unExistingUser);
        assertEquals(NUMBER_OF_BASKETS + 1, repository.count());
        userRepository.delete(unExistingUser);
        assertEquals(NUMBER_OF_BASKETS, repository.count());
        assertNull(repository.findOne(unExistingBasket.getId()));
    }

    /**
     * Temp that when a new product is added into the basket set and the basket is saved,
     * the product for the basket will be saved int he database.
     */
    @Test
    public void testAddProduct() {
        basket2.getProducts().add(product1);
        Basket basket = repository.save(basket2);
        assertTrue(SetUtils.equals(basket2.getProducts(), basket.getProducts()));
        Basket updatedBasket = repository.findOne(basket2.getId());
        assertTrue(SetUtils.equals(basket2.getProducts(), updatedBasket.getProducts()));
    }

    /**
     * Temp that when a new product who is empty and only contains the his id is added into the basket set and the
     * basket is saved, the product for the basket will be saved int he database.
     */
    @Test
    public void testAddProductWhenTheProductContainsOnlyTheId() {
        basket1.getProducts().add(new Product(product3.getId()));
        Basket basket = repository.save(basket1);
        assertEquals(basket1.getProducts().size(), basket.getProducts().size());
        Basket updatedBasket = repository.findOne(basket1.getId());
        assertEquals(basket.getProducts().size(), updatedBasket.getProducts().size());
    }

    /**
     * Temp that when a new product who is empty is and only contains his id is added into the basket set,
     * the basket is saved, and the basket already contains a product with the same id, a new product will
     * not be added for the basket in the database.
     */
    @Test
    public void testAddProductWhenNewProductContainsAlreadyExistingId() {
        assertTrue(basket2.getProducts().add(new Product(product2.getId())));
        Basket basket = repository.save(basket2);
        assertEquals(basket2.getProducts().size() - 1, basket.getProducts().size());
        Basket updatedBasket = repository.findOne(basket2.getId());
        assertEquals(basket2.getProducts().size() - 1, updatedBasket.getProducts().size());
    }

    /**
     * Temp that when a product is removed from the basket set, and the basket is saved, the product
     * for the basket will also be deleted from the database.
     */
    @Test
    public void testRemoveProduct() {
        assertTrue(basket1.getProducts().remove(product1));
        Basket basket = repository.save(basket1);
        assertTrue(SetUtils.equals(basket1.getProducts(), basket.getProducts()));
        Basket updatedBasket = repository.findOne(basket1.getId());
        assertTrue(SetUtils.equals(basket1.getProducts(), updatedBasket.getProducts()));
    }

    /**
     * Temp that findByIdAndProductsId will search for the basket by the basket id and the product id.
     */
    @Test
    public void testFindByProductsId() {
        Basket basket = repository.findByIdAndProductsId(basket1.getId(), product1.getId());
        this.assertBasket(basket1, basket);
    }

    /**
     * Temp that findByIdAndProductsId will return null when a record can;t be find.
     */
    @Test
    public void testFindByProductsIdOnUnExistingRecord() {
        assertNull(repository.findByIdAndProductsId(basket2.getId(), product1.getId()));
    }

    /**
     * Temp that countByBaskets products will return the number of products in the given basket.
     */
    @Test
    public void testCountByProducts() {
        final int size = basket1.getProducts().size();
        assertEquals(size, productRepository.countByBasketsId(basket1.getId()));
    }
}
