package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.ProductLikeId;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.repository.ProductLikeRepository;
import com.wp.finki.ukim.mk.catalogware.service.ProductLikeService;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Borce on 02.09.2016.
 */
@Service
public class ProductLikeServiceImpl implements ProductLikeService {

    @Autowired
    private ProductLikeRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public List<ProductLike> getAll() {
        return repository.findAll();
    }

    @Override
    public ProductLike get(ProductLikeId id) {
        return repository.findOne(id);
    }

    @Override
    public ProductLike get(long userId, long productId) {
        return this.get(new ProductLikeId(new User(userId), new Product(productId)));
    }

    @Override
    public boolean exists(ProductLikeId id) {
        return this.get(id) != null;
    }

    @Override
    public boolean exists(long userId, long productId) {
        return this.get(userId, productId) != null;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public ProductLike store(ProductLikeId id, short rating) {
        if (this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("like on the product with id %d from the user with id %d already added",
                            id.getProduct().getId(), id.getUser().getId()));
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(String.format("invalid rating, %d", rating));
        }
        ProductLike like = new ProductLike(id, rating);
        return repository.save(like);
    }

    @Override
    public ProductLike store(long userId, long productId, short rating) {
        return this.store(new ProductLikeId(new User(userId), new Product(productId)), rating);
    }

    @Override
    public ProductLike update(ProductLikeId id, short rating) {
        ProductLike like = this.get(id);
        if (like == null) {
            throw new IllegalArgumentException(String
                    .format("can't update like, like from user with id %d on product with id %d don't exists",
                            id.getUser().getId(), id.getProduct().getId()));
        }
        like.setRating(rating);
        return repository.save(like);
    }

    @Override
    public ProductLike update(long userId, long productId, short rating) {
        return this.update(new ProductLikeId(new User(userId), new Product(productId)), rating);
    }

    @Override
    public boolean delete(ProductLikeId id) {
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("can't update like, like from user with id %d on product with id %d don't exists",
                            id.getUser().getId(), id.getProduct().getId()));
        }
        repository.delete(id);
        return this.exists(id);
    }

    @Override
    public boolean delete(long userId, long productId) {
        return this.delete(new ProductLikeId(new User(userId), new Product(productId)));
    }
}
