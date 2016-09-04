package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 27.08.2016.
 */
@Entity
@Table(name = "categories")
public class Category extends BaseModel implements Serializable {

    @Column(name = "name", unique = true, nullable = false)
    @NotNull(message = "Name field is required")
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Set<Product> products;

    public Category() {
    }

    public Category(long id, String name, Set<Product> products) {
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Category)) {
            return false;
        }
        Category category = (Category) obj;
        return super.equalFields(this.id, category.getId()) &&
                super.equalFields(this.name, category.getName());
    }
}
