package com.wp.finki.ukim.mk.catalogware.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Borce on 27.08.2016.
 */
@Embeddable
public class ProductLikeId implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    public ProductLikeId() {
    }

    public ProductLikeId(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof ProductLikeId)) {
            return false;
        }
        ProductLikeId id = (ProductLikeId) obj;
        return this.user != null && this.user.equals(id.getUser()) &&
                this.product != null && this.product.equals(id.getProduct());
    }
}
