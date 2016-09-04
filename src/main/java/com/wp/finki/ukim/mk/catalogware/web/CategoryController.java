package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.exception.BadRequestException;
import com.wp.finki.ukim.mk.catalogware.exception.ResourceNotFoundException;
import com.wp.finki.ukim.mk.catalogware.model.Category;
import com.wp.finki.ukim.mk.catalogware.model.response.Response;
import com.wp.finki.ukim.mk.catalogware.service.CategoryService;
import com.wp.finki.ukim.mk.catalogware.utils.ValidationErrorsMessageConverter;
import com.wp.finki.ukim.mk.catalogware.validator.CategoryRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Borce on 03.09.2016.
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryRequestValidator validator;

    @InitBinder("category")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping
    public List<Category> index() {
        logger.info("list categoires");
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Category get(@PathVariable long id) {
        logger.info(String.format("get category with id %d", id));
        Category category = service.get(id);
        if (category == null) {
            logger.info(String.format("category with id %d not found", id));
            throw new ResourceNotFoundException();
        }
        return category;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response store(@Valid @RequestBody Category category, BindingResult bindingResult,
                          ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        logger.info("creating new category");
        if (bindingResult.hasErrors()) {
            logger.info("request data to created a category is not valid");
            throw new BadRequestException(validationErrorsMessageConverter
                    .getErrors(bindingResult.getFieldErrors()));
        }
        service.store(category);
        logger.info("new category saved");
        return new Response(201, "Category created", "The category was successfully created");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}")
    public Response update(@PathVariable long id, @Valid @RequestBody Category category, BindingResult bindingResult,
                           ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        logger.info(String.format("updating cateogry with id %d", id));
        if (bindingResult.hasErrors()) {
            logger.info("request data to update the category is not valid");
            throw new BadRequestException(validationErrorsMessageConverter
                    .getErrors(bindingResult.getFieldErrors()));
        }
        try {
            service.update(id, category);
            logger.info(String.format("category with id %d is updated", id));
        } catch (IllegalArgumentException exp) {
            logger.info(String.format("category with id %d not found", id));
            throw new ResourceNotFoundException();
        }
        return new Response(200, "Category Updated", "The category was updated successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        try {
            service.delete(id);
            logger.info(String.format("category with id %d is deleted", id));
        } catch (IllegalArgumentException exp) {
            logger.info(String.format("category with id %d not found", id));
            throw new ResourceNotFoundException();
        }
    }
}
