package com.wp.finki.ukim.mk.catalogware.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Borce on 27.08.2016.
 */
@Embeddable
public class ProductLikeId implements Serializable {

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

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
}
