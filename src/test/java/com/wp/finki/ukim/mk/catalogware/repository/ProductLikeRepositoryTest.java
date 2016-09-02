package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.ProductLikeId;
import com.wp.finki.ukim.mk.catalogware.model.User;
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
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Borce on 01.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogwareApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ProductLikeRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLikeRepository repository;

    @Autowired
    private ApplicationContext context;

    private User admin = new User(1, "Admin", "admin@admin.com", "pass", new Date(), User.Role.ADMIN);
    private User user1 = new User(2, "User 1", "user@user.com", "pass", new Date(), User.Role.CUSTOMER);
    private User user2 = new User(3, "User 2", "user-2@user.com", "pass", new Date(), User.Role.CUSTOMER);
    private Product product1 = new Product(1, "Product 1", "Test Product 1", 123, null, new Date(), new Date(), admin);
    private Product product2 = new Product(2, "Product 2", "Test Product 2", 123, null, new Date(), new Date(), admin);
    private final int NUMBER_OF_LIKES = 3;
    private ProductLike like1 = new ProductLike(new ProductLikeId(user1, product1), (short) 5);
    private ProductLike like2 = new ProductLike(new ProductLikeId(user1, product2), (short) 3);
    private ProductLike like3 = new ProductLike(new ProductLikeId(user2, product1), (short) 1);
    private ProductLike unExistingLike = new ProductLike(new ProductLikeId(user2, product2), (short) 2);

    @Before
    public void setup() {
        userRepository.save(admin);
        userRepository.save(user1);
        userRepository.save(user2);
        productRepository.save(product1);
        productRepository.save(product2);
        repository.save(like1);
        repository.save(like2);
        repository.save(like3);
    }

    @After
    public void shutdown() {
        repository.deleteAll();
        productRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Product.class);
        userRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, User.class);
    }

    /**
     * Test that findAll will return all likes in the dastabase.
     */
    @Test
    public void testFindAll() {
        List<ProductLike> likes = repository.findAll();
        assertEquals(NUMBER_OF_LIKES, likes.size());
        int counter = 0;
        for (ProductLike like : likes) {
            ProductLikeId id = like.getPk();
            long userId = id.getUser().getId();
            long productId = id.getProduct().getId();
            if (like1.getPk().getUser().getId().equals(userId) &&
                    like1.getPk().getProduct().getId().equals(productId)) {
                assertEquals(like1, like);
                counter++;
            } else if (like2.getPk().getUser().getId().equals(userId) &&
                    like2.getPk().getProduct().getId().equals(productId)) {
                assertEquals(like2, like);
                counter++;
            } else if (like3.getPk().getUser().getId().equals(userId) &&
                    like3.getPk().getProduct().getId().equals(productId)) {
                assertEquals(like3, like);
                counter++;
            }
        }
        assertEquals(NUMBER_OF_LIKES, counter);
    }

    /**
     * Test that findOne will return the like when the id exists in the database.
     */
    @Test
    public void testFindOne() {
        ProductLike like = repository.findOne(like2.getPk());
        assertEquals(like2, like);
    }

    /**
     * Test that behavior when findOne is called with ProductLikeId which contains user and product that
     * have empty data and only the primary key is set.
     */
    @Test
    public void testFindOneWhenTheUserAndProductContainsOnlyThePrimaryKey() {
        User user = new User(like1.getPk().getUser().getId(), null, null, null, null, null);
        Product product = new Product(like1.getPk().getProduct().getId(), null, null, 0, null, null, null, null);
        ProductLikeId id = new ProductLikeId(user, product);
        ProductLike like = repository.findOne(id);
        assertNotEquals(like1, like);
        assertEquals(like1.getRating(), like.getRating());
        assertEquals(like1.getPk().getUser().getId(), like.getPk().getUser().getId());
        assertEquals(like1.getPk().getProduct().getId(), like.getPk().getProduct().getId());
    }

    /**
     * Test that findOne will return null when the id don't exists in the database.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        assertNull(repository.findOne(unExistingLike.getPk()));
    }

    /**
     * Test that count will return the number of users in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_LIKES, repository.count());
    }

    /**
     * Test that save method will save a new like in the database when the id don't exists in the database.
     */
    @Test
    public void testStore() {
        ProductLike like = repository.save(unExistingLike);
        assertEquals(NUMBER_OF_LIKES + 1, repository.count());
        assertEquals(unExistingLike, like);
        ProductLike savedLike = repository.findOne(unExistingLike.getPk());
        assertEquals(unExistingLike, savedLike);
    }

    /**
     * Test that save will update the like data when the id exists in the database.
     */
    @Test
    public void testUpdate() {
        like3.setRating((short) 4);
        ProductLike like = repository.save(like3);
        assertEquals(NUMBER_OF_LIKES, repository.count());
        assertEquals(like3, like);
        ProductLike updatedLike = repository.findOne(like3.getPk());
        assertEquals(like3, updatedLike);
    }

    /**
     * Test that delete will remove the like from the database.
     */
    @Test
    public void testDelete() {
        repository.delete(like1.getPk());
        assertEquals(NUMBER_OF_LIKES - 1, repository.count());
        assertNull(repository.findOne(like1.getPk()));
    }
}
