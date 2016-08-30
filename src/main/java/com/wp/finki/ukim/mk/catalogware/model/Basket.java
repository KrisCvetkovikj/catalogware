package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Borce on 27.08.2016.
 */
@Entity
@Table(name = "baskets")
public class Basket extends BaseModel implements Serializable {

    @Column(name = "updated_at")
    private Date updatedAt;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "baskets_products",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public Basket() {
    }

    public Basket(long id, Date updatedAt, User user, List<Product> products) {
        super(id);
        this.updatedAt = updatedAt;
        this.user = user;
        this.products = products;
    }

    public Basket(long id, Date updatedAt, User user) {
        this(id, updatedAt, user, null);
    }

    public Basket(Date updatedAt, User user) {
        this(0, updatedAt, user);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
