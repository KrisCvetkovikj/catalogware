package com.wp.finki.ukim.mk.catalogware.validator;

import com.wp.finki.ukim.mk.catalogware.model.Category;
import com.wp.finki.ukim.mk.catalogware.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Borce on 03.09.2016.
 */
@Component
public class CategoryRequestValidator implements Validator {

    @Autowired
    private CategoryService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Category.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;
        validateName(category.getName(), errors);
    }

    private void validateName(String name, Errors errors) {
        if (name != null) {
            Category category = service.findByName(name);
            if (category != null) {
                errors.rejectValue("name", "notUnique", "A category with the given name already exists");
            }
        }
    }
}
