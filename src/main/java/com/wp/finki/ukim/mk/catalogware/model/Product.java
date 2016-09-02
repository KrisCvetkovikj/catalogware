package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 27.08.2016.
 */
@Entity
@Table(name = "products")
public class Product extends BaseModel implements Serializable {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 10000, nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @JsonIgnore
    @Column(name = "image", length = 10240)
    private byte[] image;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "products_categories",
            joinColumns =  @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
    private Set<Basket> baskets;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
    private Set<Order> orders;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.product")
    private Set<ProductLike> likes;

    public Product() {
    }

    public Product(long id, String name, String description, double price, byte[] image, Date createdAt,
                   Date updatedAt, User admin, Set<Category> categories, Set<Basket> baskets,
                   Set<Order> orders, Set<ProductLike> likes) {
        super(id);
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
        this.likes = likes;
    }

    public Product(long id, String name, String description, double price, byte[] image,
                   Date createdAt, Date updatedAt, User admin) {
        this(id, name, description, price, image, createdAt, updatedAt, admin, null, null, null, null);
    }

    public Product(String name, String description, double price, byte[] image, Date createdAt,
                   Date updatedAt, User admin) {
        this(0, name, description, price, image, createdAt, updatedAt, admin);
    }

    public Product(long id) {
        super(id);
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(Set<Basket> baskets) {
        this.baskets = baskets;
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

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Product)) {
            return false;
        }
        Product product = (Product) obj;
        return super.equalFields(this.id, product.getId()) &&
                super.equalFields(this.name, product.getName()) &&
                super.equalFields(this.description, product.description) &&
                this.price == product.getPrice() &&
                ((this.image == null && product.image == null) || Arrays.equals(image, product.getImage())) &&
                super.equalFields(this.createdAt, product.getCreatedAt()) &&
                super.equalFields(this.updatedAt, product.getUpdatedAt());
    }
}
