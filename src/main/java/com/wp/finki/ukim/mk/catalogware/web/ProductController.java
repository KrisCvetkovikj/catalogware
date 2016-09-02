package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void store(@RequestBody Product product) {

    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable long id, @RequestBody Product product) {
        
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {

    }
}
