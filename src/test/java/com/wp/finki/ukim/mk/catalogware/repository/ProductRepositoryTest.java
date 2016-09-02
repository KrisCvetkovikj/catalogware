package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Category;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.utils.SetUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Borce on 31.08.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogwareApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ApplicationContext context;

    @Value("${app.test.img}")
    String testImgPath;

    private User admin1 = new User(1, "Admin 1", "admin-1@admin.com", "pass", new Date(), User.Role.ADMIN);
    private User admin2 = new User(2, "Admin 2", "admin-2@admin.com", "pass", new Date(), User.Role.ADMIN);

    private Category category1 = new Category(1, "Category 1");
    private Category category2 = new Category(2, "Category 2");
    private Category category3 = new Category(3, "Category 3");

    private final int NUMBER_OF_PRODUCTS = 3;
    private final long product1Id = 1;
    private Product product1 = new Product(product1Id, "Product 1", "Test product 1", 101.32,
            null, new Date(), new Date(), admin1);
    private final long product2Id = 2;
    private Product product2 = new Product(product2Id, "Product 2", "Test product 2", 123,
            null, new Date(), new Date(), admin2);
    private final long product3Id = 3;
    private Product product3 = new Product(product3Id, "Product 3", "Test product 3", 236.21,
            null, new Date(), new Date(), admin1);
    private final long unExistingProductId = 4;
    private Product unExistingProduct = new Product(unExistingProductId, "Un Existing product",
            "Test un existing product", 125.64, null, new Date(), new Date(), admin2);

    @Before
    public void setup() {
        byte[] image = TestUtils.readFile(this.getClass().getClassLoader(), testImgPath);
        product1.setImage(image);
        product2.setImage(image);
        product3.setImage(image);
        unExistingProduct.setImage(image);

        Set<Category> categories1 = new HashSet<>();
        Set<Category> categories2 = new HashSet<>();
        Set<Category> categories3 = new HashSet<>();
        categories1.add(category1);
        categories1.add(category2);
        categories2.add(category2);
        categories2.addAll(categories3);
        categories3.add(category1);
        categories3.add(category3);
        product1.setCategories(categories1);
        product2.setCategories(categories2);
        product3.setCategories(categories3);

        userRepository.save(admin1);
        userRepository.save(admin2);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        repository.save(product1);
        repository.save(product2);
        repository.save(product3);
    }

    @After
    public void shutdown() {
        repository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Product.class);
        categoryRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Category.class);
        userRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, User.class);
    }

    /**
     * Assert that the given product data is same as the actual product.
     *
     * @param actualProduct actual product
     * @param product       product to be checked
     */
    private void assertProduct(Product actualProduct, Product product) {
        assertEquals(actualProduct, product);
        assertEquals(actualProduct.getAdmin(), product.getAdmin());
        assertTrue(SetUtils.equals(actualProduct.getCategories(), product.getCategories()));
    }

    /**
     * Test that findAll will return all products in the database.
     */
    @Test
    public void testFindAll() {
        List<Product> products = repository.findAll();
        assertEquals(NUMBER_OF_PRODUCTS, products.size());
        int counter = 0;
        for (Product product : products) {
            if (product.getId() == product1Id) {
                this.assertProduct(product1, product);
                counter++;
            } else if (product.getId() == product2Id) {
                this.assertProduct(product2, product);
                counter++;
            } else if (product.getId() == product3Id) {
                this.assertProduct(product3, product);
                counter++;
            }
        }
        assertEquals(NUMBER_OF_PRODUCTS, counter);
    }

    /**
     * Test that findOne will return the product when the id exists in the database.
     */
    @Test
    public void testFindOne() {
        Product product = repository.findOne(product1Id);
        this.assertProduct(product1, product);
    }

    /**
     * Test that findOne will return null when the id don't exists in the database.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        assertNull(repository.findOne(unExistingProductId));
    }

    /**
     * Test that count will return the number of products in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_PRODUCTS, repository.count());
    }

    /**
     * Test that store will store the product data in the database.
     */
    @Test
    public void testStore() {
        unExistingProduct.setCategories(new HashSet<>(Arrays.asList(category2, category1)));
        Product product = repository.save(unExistingProduct);
        this.assertProduct(unExistingProduct, product);
        Product savedProduct = repository.findOne(unExistingProductId);
        this.assertProduct(unExistingProduct, savedProduct);
    }

    /**
     * Test that save will update the product data when the product id exists in the database.
     */
    @Test
    public void update() {
        product1.setName("Updated product");
        product1.getCategories().add(category3);
        Product product = repository.save(product1);
        assertEquals(NUMBER_OF_PRODUCTS, repository.count());
        this.assertProduct(product1, product);
        Product updatedProduct = repository.findOne(product1Id);
        this.assertProduct(product1, updatedProduct);
    }

    /**
     * Test that delete method will remove the product from the database.
     */
    @Test
    public void testDelete() {
        repository.delete(product1Id);
        assertEquals(NUMBER_OF_PRODUCTS - 1, repository.count());
        assertNull(repository.findOne(product1Id));
    }
}
