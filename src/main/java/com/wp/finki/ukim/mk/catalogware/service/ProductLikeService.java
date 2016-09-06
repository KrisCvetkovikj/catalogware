package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.ProductLikeId;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;

import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 02.09.2016.
 */
public interface ProductLikeService {

    List<ProductLike> getAll();

    ProductLike get(ProductLikeId id);

    ProductLike get(long userId, long productId);

    boolean exists(ProductLikeId id);

    boolean exists(long userId, long productId);

    long count();

    ProductLike store(ProductLikeId id, short rating);

    ProductLike store(long userId, long productId, short rating);

    ProductLike update(ProductLikeId id, short rating);

    ProductLike update(long userId, long productId, short rating);

    void delete(ProductLikeId id);

    void delete(long userId, long productId);

    Set<ProductLike> getProductLikes(long productId);

    Set<ProductLike> getUserLikes(Long userId);
}