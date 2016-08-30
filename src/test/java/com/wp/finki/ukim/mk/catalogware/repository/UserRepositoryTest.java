package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Category;
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
 * Created by Borce on 30.08.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogwareApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ApplicationContext context;

    private final int NUMBER_OF_USERS = 3;
    private final long user1Id = 1;
    private User user1 = new User(user1Id, "User 1", "user@user-1.com", "password", new Date(), User.Role.CUSTOMER);
    private final long user2Id = 2;
    private User user2 = new User(user2Id, "User 2", "user@user-2.com", "password", new Date(), User.Role.ADMIN);
    private final long user3Id = 3;
    private User user3 = new User(user3Id, "User 3", "user@user-3.com", "password", new Date(), User.Role.CUSTOMER);
    private final long unExistingUserId = 4;
    private User unExistingUser = new User(unExistingUserId, "UnExistingUser", "un@user.com", "password",
            new Date(), User.Role.CUSTOMER);

    @Before
    public void setup() {
        repository.save(user1);
        repository.save(user2);
        repository.save(user3);
    }

    @After
    public void shutdown() {
        TestUtils.truncateModelTable(context, User.class);
    }

    /**
     * Assert that the actual user is same with the given user.
     *
     * @param actualUser actual user
     * @param user       user to be asserted
     */
    private void assertUser(User actualUser, User user) {
        assertEquals(actualUser.getName(), user.getName());
        assertEquals(actualUser.getEmail(), user.getEmail());
        assertEquals(actualUser.getPassword(), user.getPassword());
        assertEquals(actualUser.getCreatedAt(), user.getCreatedAt());
        assertEquals(actualUser.getRole(), user.getRole());
    }

    /**
     * Test that findAll will return all saved users in the database.
     */
    @Test
    public void testFindAll() {
        List<User> users = repository.findAll();
        assertEquals(NUMBER_OF_USERS, users.size());
        int counter = 0;
        for (User user : users) {
            if (user.getId() == user1Id) {
                this.assertUser(user1, user);
                counter++;
            } else if (user.getId() == user2Id) {
                this.assertUser(user2, user);
                counter++;
            } else if (user.getId() == user3Id) {
                this.assertUser(user3, user);
                counter++;
            }
        }
        assertEquals(NUMBER_OF_USERS, counter);
    }

    /**
     * Test that findOne will return the user when the id exists.
     */
    @Test
    public void testFindOne() {
        User user = repository.findOne(user1Id);
        assertNotNull(user);
        this.assertUser(user1, user);
    }

    /**
     * Test that findOne will return null when the id don't exists.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        User user = repository.findOne(unExistingUserId);
        assertNull(user);
    }

    /**
     * Test that count method will return the number of users in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_USERS, repository.count());
    }

    /**
     * Test that save method will store a new user when the uer id don't exists.
     */
    @Test
    public void testStore() {
        User user = repository.save(unExistingUser);
        assertEquals(NUMBER_OF_USERS + 1, repository.count());
        assertNotNull(user);
        this.assertUser(unExistingUser, user);
        User findUser = repository.findOne(unExistingUserId);
        assertNotNull(findUser);
        this.assertUser(unExistingUser, findUser);
    }

    /**
     * Test that save method will update user data when the user id exists.
     */
    @Test
    public void testUpdate() {
        user1.setEmail("new@user.com");
        User user = repository.save(user1);
        assertEquals(NUMBER_OF_USERS, repository.count());
        this.assertUser(user1, user);
    }

    /**
     * Test that delete method will remove a existing user from the database.
     */
    @Test
    public void testDelete() {
        repository.delete(user1);
        assertEquals(NUMBER_OF_USERS - 1, repository.count());
        assertNull(repository.findOne(user1Id));
    }
}
