package com.wp.finki.ukim.mk.catalogware.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Borce on 27.08.2016.
 */
@Entity
@Table(name = "products_likes")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "pk.product", joinColumns = @JoinColumn(name = "product_id"))
})
public class ProductLike implements Serializable {

    @EmbeddedId
    private ProductLikeId pk;

    @Column(name = "rating")
    private short rating;

    public ProductLike() {
    }

    public ProductLike(ProductLikeId pk, short rating) {
        this.pk = pk;
        this.rating = rating;
    }

    public ProductLikeId getPk() {
        return pk;
    }

    public void setPk(ProductLikeId pk) {
        this.pk = pk;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof ProductLike)) {
            return false;
        }
        ProductLike like = (ProductLike) obj;
        return this.pk != null && this.pk.equals(like.pk) &&
                this.rating == like.getRating();
    }
}
