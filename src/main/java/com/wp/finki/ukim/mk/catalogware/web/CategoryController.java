package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.exception.BadRequestException;
import com.wp.finki.ukim.mk.catalogware.exception.ResourceNotFound;
import com.wp.finki.ukim.mk.catalogware.model.Category;
import com.wp.finki.ukim.mk.catalogware.model.response.Response;
import com.wp.finki.ukim.mk.catalogware.service.CategoryService;
import com.wp.finki.ukim.mk.catalogware.utils.ValidationErrorsMessageConverter;
import com.wp.finki.ukim.mk.catalogware.validator.CategoryRequestValidator;
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
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Category get(@PathVariable long id) {
        Category category = service.get(id);
        if (category == null) {
            throw new ResourceNotFound();
        }
        return category;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response store(@Valid @RequestBody Category category, BindingResult bindingResult,
                          ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(validationErrorsMessageConverter
                    .getErrors(bindingResult.getFieldErrors()));
        }
        service.store(category);
        return new Response(201, "Category created", "The category was successfully created");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public Response update(@PathVariable long id, @Valid @RequestBody Category category, BindingResult bindingResult,
                           ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(validationErrorsMessageConverter
                    .getErrors(bindingResult.getFieldErrors()));
        }
        service.update(id, category);
        return new Response(200, "Category Updated", "The category was updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
