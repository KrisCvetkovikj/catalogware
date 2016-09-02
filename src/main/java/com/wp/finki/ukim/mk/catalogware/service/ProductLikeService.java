package com.wp.finki.ukim.mk.catalogware.service;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.ProductLikeId;
import com.wp.finki.ukim.mk.catalogware.model.User;

import java.util.List;

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

    boolean delete(ProductLikeId id);

    boolean delete(long userId, long productId);
}