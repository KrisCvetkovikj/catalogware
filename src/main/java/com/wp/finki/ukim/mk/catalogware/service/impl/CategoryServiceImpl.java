package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.exception.CategoryChangeFailedException;
import com.wp.finki.ukim.mk.catalogware.model.Category;
import com.wp.finki.ukim.mk.catalogware.repository.CategoryRepository;
import com.wp.finki.ukim.mk.catalogware.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category get(long id) {
        return repository.findOne(id);
    }

    @Override
    public Category findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public boolean exists(long id) {
        return this.get(id) != null;
    }

    @Override
    public long count() {
        return repository.count();
    }

    private void validateDate(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category can't be null");
        }
        if (category.getName() == null) {
            throw new IllegalArgumentException("category name can;t be null");
        }
    }

    @Override
    public Category store(Category category) {
        this.validateDate(category);
        if (this.findByName(category.getName()) != null) {
            throw new IllegalArgumentException(String
                    .format("cant save category, category with name %s already exists", category.getName()));
        }
        Category result = repository.save(category);
        if (result == null) {
            throw new CategoryChangeFailedException("Error occurred while storing the category");
        }
        return result;
    }

    @Override
    public Category store(String name) {
        return this.store(new Category(name));
    }

    @Override
    public Category update(long id, Category category) {
        this.validateDate(category);
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't update category, category with id %d don't exists", id));
        }
        category.setId(id);
        Category result = repository.save(category);
        if (result == null) {
            throw new CategoryChangeFailedException("Error occurred while updating the category");
        }
        return result;
    }

    @Override
    public boolean delete(long id) {
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can;t delete category, category with id %d don't exists", id));
        }
        repository.delete(id);
        boolean deleted = !this.exists(id);
        if (!deleted) {
            throw new CategoryChangeFailedException("Error occurred while deleting the category");
        }
        return true;
    }
}
