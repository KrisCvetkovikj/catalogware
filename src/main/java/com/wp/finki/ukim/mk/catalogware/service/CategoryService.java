package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Category;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
public interface CategoryService {

    List<Category> getAll();

    Category get(long id);

    Category findByName(String name);

    boolean exists(long id);

    long count();

    Category store(Category category);

    Category store(String name);

    Category update(long id, Category category);

    boolean delete(long id);
}
