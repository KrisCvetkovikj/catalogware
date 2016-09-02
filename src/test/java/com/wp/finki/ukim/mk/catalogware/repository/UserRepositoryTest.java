package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
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
        repository.deleteAll();
        TestUtils.resetTableAutoincrement(context, User.class);
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
                assertEquals(user1, user);
                counter++;
            } else if (user.getId() == user2Id) {
                assertEquals(user2, user);
                counter++;
            } else if (user.getId() == user3Id) {
                assertEquals(user3, user);
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
        assertEquals(user1, user);
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
     * Test that findByName will return the user when the user name exists.
     */
    @Test
    public void testFindByName() {
        User user = repository.findByName(user2.getName());
        assertEquals(user2, user);
    }

    /**
     * Test that findByName will return null when the user name don't exists.
     */
    @Test
    public void testFindByNameOnUnExistingName() {
        User user = repository.findByName(unExistingUser.getName());
        assertNull(user);
    }

    /**
     * Test that findByEmail will return the user when the user email exists.
     */
    @Test
    public void testFindByEmail() {
        User user = repository.findByEmail(user3.getEmail());
        assertEquals(user3, user);
    }

    /**
     * Test that findByEmail will return null when the user email don't exists.
     */
    @Test
    public void testFindByEmailOnUnExistingEmail() {
        User user = repository.findByEmail(unExistingUser.getEmail());
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
        assertEquals(unExistingUser, user);
        User savedUser = repository.findOne(unExistingUserId);
        assertEquals(unExistingUser, savedUser);
    }

    /**
     * Test that save method will update user data when the user id exists.
     */
    @Test
    public void testUpdate() {
        user1.setEmail("new@user.com");
        User user = repository.save(user1);
        assertEquals(NUMBER_OF_USERS, repository.count());
        assertEquals(user1, user);
        User updatedUser = repository.findOne(user1Id);
        assertEquals(user1, updatedUser);
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
