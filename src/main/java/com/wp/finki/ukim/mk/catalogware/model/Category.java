package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Borce on 27.08.2016.
 */
@Entity
@Table(name = "categories")
public class Category extends BaseModel implements Serializable {

    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private List<Product> products;

    public Category() {
    }

    public Category(long id, String name, List<Product> products) {
        super(id);
        this.name = name;
        this.products = products;
    }

    public Category(long id, String name) {
        this(id, name, null);
    }

    public Category(String name) {
        this(0, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
