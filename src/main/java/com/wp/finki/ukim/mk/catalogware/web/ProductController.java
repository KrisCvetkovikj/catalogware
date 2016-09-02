package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.response.Response;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> index() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Product get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response store(@RequestBody Product product) {
        service.store(product);
        return new Response(201, "Product created", "The product was created successfully");
    }

    @PutMapping(value = "/{id}")
    public Response update(@PathVariable long id, @RequestBody Product product) {
        service.update(id, product);
        return new Response(200, "Product updated", "The product was updated successfully");
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
