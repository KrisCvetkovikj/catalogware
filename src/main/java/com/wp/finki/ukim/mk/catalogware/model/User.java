package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Borce on 26.08.2016.
 */
@Entity
@Table(name = "users")
public class User extends BaseModel implements Serializable {

    public enum Role {
        ADMIN,
        CUSTOMER
    }

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Basket basket;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Order> orders;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "admin")
    private Set<Product> products;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.user")
    private Set<ProductLike> likes;

    public User() {
    }

    public User(long id, String name, String email, String password, Date createdAt, Role role,
                Basket basket, Set<Order> orders, Set<ProductLike> likes, Set<Product> products) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.basket = basket;
        this.orders = orders;
        this.likes = likes;
        this.products = products;
    }

    public User(long id, String name, String email, String password, Date createdAt, Role role) {
        this(id, name, email, password, createdAt, role, null, null, null, null);
    }

    public User(String name, String email, String password, Date createdAt, Role role) {
        this(0, name, email, password, createdAt, role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<ProductLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<ProductLike> likes) {
        this.likes = likes;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return super.equalFields(this.id, user.getId()) &&
                super.equalFields(this.name, user.getName()) &&
                super.equalFields(this.email, user.getEmail()) &&
                super.equalFields(this.password, user.getPassword()) &&
                super.equalFields(this.createdAt, user.getCreatedAt()) &&
                super.equalFields(this.role, user.getRole());
    }
}
