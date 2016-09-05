package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Category;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Borce on 31.08.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogwareApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ApplicationContext context;

    private final int NUMBER_OF_CATEGORIES = 3;
    private final long category1Id = 1;
    private Category category1 = new Category(category1Id, "Category 1");
    private final long category2Id = 2;
    private Category category2 = new Category(category2Id, "Category 2");
    private final long category3Id = 3;
    private Category category3 = new Category(category3Id, "Category 3");
    private final long unExistingCategoryId = 4;
    private Category unExistingCategory = new Category(unExistingCategoryId, "UnExistingCategory");

    @Before
    public void setup() {
        repository.save(category1);
        repository.save(category2);
        repository.save(category3);
    }

    @After
    public void shutdown() {
        repository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Category.class);
    }

    /**
     * Test that findAll method will return all saved categories in the database.
     */
    @Test
    public void testFindAll() {
        List<Category> categories = repository.findAll();
        assertEquals(NUMBER_OF_CATEGORIES, categories.size());
        int counter = 0;
        for (Category category : categories) {
            if (category.getId() == category1Id) {
                assertEquals(category1, category);
                counter++;
            } else if (category.getId() == category2Id) {
                assertEquals(category2, category);
                counter++;
            } else if (category.getId() == category3Id) {
                assertEquals(category3, category);
                counter++;
            }
        }
        assertEquals(NUMBER_OF_CATEGORIES, counter);
    }

    /**
     * Test that fineOne method will return the category when the id exists.
     */
    @Test
    public void testFindOne() {
        Category category = repository.findOne(category1Id);
        assertEquals(category1, category);
    }

    /**
     * Test that findOne will return null when the category id don't exist.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        Category category = repository.findOne(unExistingCategoryId);
        assertNull(category);
    }

    /**
     * Test that findByName will return the category when the name exists in the database.
     */
    @Test
    public void testFindByName() {
        Category category = repository.findByName(category3.getName());
        assertEquals(category3, category);
    }

    /**
     * Test that findByName will return null when the name don't exists.
     */
    @Test
    public void testFindByNameOnUnExistingName() {
        Category category = repository.findByName(unExistingCategory.getName());
        assertNull(category);
    }

    /**
     * Test then count will return the number of categories in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_CATEGORIES, repository.count());
    }

    /**
     * Test that save method will save a new category when the id don;t exists in the database.
     */
    @Test
    public void testStore() {
        Category category = repository.save(unExistingCategory);
        assertEquals(NUMBER_OF_CATEGORIES + 1, repository.count());
        assertEquals(unExistingCategory, category);
        Category savedCategory = repository.findOne(unExistingCategoryId);
        assertEquals(unExistingCategory, savedCategory);
    }

    /**
     * Test that save method will update a existing row int he database when the id exists.
     */
    @Test
    public void testUpdate() {
        category2.setName("Category 2 Updated");
        Category category = repository.save(category2);
        assertEquals(NUMBER_OF_CATEGORIES, repository.count());
        assertEquals(category2, category);
        Category updatedCategory = repository.findOne(category2Id);
        assertEquals(category2, updatedCategory);
    }

    /**
     * Test that delete will remove a existing row from the database.
     */
    @Test
    public void testDelete() {
        repository.delete(category1Id);
        assertEquals(NUMBER_OF_CATEGORIES - 1, repository.count());
        assertNull(repository.findOne(category1Id));
    }
}
