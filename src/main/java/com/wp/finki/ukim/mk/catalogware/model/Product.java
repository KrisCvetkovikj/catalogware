package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * Created by Borce on 27.08.2016.
 */
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "price")
    private double price;

    @JsonIgnore
    @Column(name = "image")
    private Blob image;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_categories",
            joinColumns =  @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private List<Basket> baskets;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private List<Order> orders;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "products_likes",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> likes;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.product")
    private List<ProductLike> likes;

    public Product() {
    }

    public Product(Long id, String name, String description, double price, Blob image, Date createdAt,
                   Date updatedAt, User admin, List<Category> categories, List<Basket> baskets,
                   List<Order> orders, List<User> likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.admin = admin;
        this.categories = categories;
        this.baskets = baskets;
        this.orders = orders;
//        this.likes = likes;
    }

    public Product(Long id, String name, String description, double price, Blob image,
                   Date createdAt, Date updatedAt) {
        this(id, name, description, price, image, createdAt, updatedAt, null, null, null, null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

//    public List<User> getLikes() {
//        return likes;
//    }
//
//    public void setLikes(List<User> likes) {
//        this.likes = likes;
//    }
}
