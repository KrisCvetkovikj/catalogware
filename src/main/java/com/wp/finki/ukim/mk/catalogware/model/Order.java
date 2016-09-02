package com.wp.finki.ukim.mk.catalogware.model;

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
@Table(name = "orders")
public class Order extends BaseModel implements Serializable {

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "finished")
    private boolean finished;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    public Order() {
    }

    public Order(long id, Date createdAt, Date updatedAt, String shippingAddress, boolean finished,
                 User user, Set<Product> products) {
        super(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shippingAddress = shippingAddress;
        this.finished = finished;
        this.user = user;
        this.products = products;
    }

    public Order(long id, Date createdAt, Date updatedAt, String shippingAddress, boolean finished, User user) {
        this(id, createdAt, updatedAt, shippingAddress, finished, user, null);
    }

    public Order(Date createdAt, Date updatedAt, String shippingAddress, boolean finished, User user) {
        this(0, createdAt, updatedAt, shippingAddress, finished, user);
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

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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
        if (!(obj instanceof Order)) {
            return false;
        }
        Order order = (Order) obj;
        return super.equalFields(this.id, order.getId()) &&
                super.equalFields(this.createdAt, order.getCreatedAt()) &&
                super.equalFields(this.shippingAddress, order.getShippingAddress()) &&
                this.finished == order.isFinished();
    }
}
