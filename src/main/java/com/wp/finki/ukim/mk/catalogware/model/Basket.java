package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 27.08.2016.
 */
@Entity
@Table(name = "baskets")
public class Basket extends BaseModel implements Serializable {

    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonIgnore
    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "baskets_products",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    public Basket() {
    }

    public Basket(long id, Date updatedAt, User user, Set<Product> products) {
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Basket)) {
            return false;
        }
        Basket basket = (Basket) obj;
        return super.equalFields(this.id, basket.getId()) &&
                super.equalFields(this.updatedAt, basket.getUpdatedAt());
    }
}
