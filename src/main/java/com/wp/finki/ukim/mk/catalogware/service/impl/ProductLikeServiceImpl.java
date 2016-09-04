package com.wp.finki.ukim.mk.catalogware.service.impl;

import com.wp.finki.ukim.mk.catalogware.exception.LikeChangeFailedException;
import com.wp.finki.ukim.mk.catalogware.exception.LikeNotFoundException;
import com.wp.finki.ukim.mk.catalogware.exception.ProductNotFoundException;
import com.wp.finki.ukim.mk.catalogware.exception.UserNotFoundException;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.ProductLikeId;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.repository.ProductLikeRepository;
import com.wp.finki.ukim.mk.catalogware.service.ProductLikeService;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Borce on 02.09.2016.
 */
@Service(value = "productLikeService")
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

    private void validateData(ProductLikeId id, short rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(String.format("invalid rating, %d", rating));
        }
        if (id == null) {
            throw new IllegalArgumentException("id can't be null");
        }
        if (id.getUser() == null) {
            throw new IllegalArgumentException("like.pk.user can't be null");
        }
        if (id.getProduct() == null) {
            throw new IllegalArgumentException("like.pk.product can't be null");
        }
        if (!userService.exists(id.getUser().getId())) {
            throw new UserNotFoundException(String.format("user with id %d don't exists", id.getUser().getId()));
        }
        if (!productService.exists(id.getProduct().getId())) {
            throw new ProductNotFoundException(String
                    .format("product with id %d don't exists", id.getProduct().getId()));
        }
    }

    @Override
    public ProductLike store(ProductLikeId id, short rating) {
        this.validateData(id, rating);
        if (this.exists(id)) {
            throw new IllegalArgumentException(String
                    .format("like on the product with id %d from the user with id %d already added",
                            id.getProduct().getId(), id.getUser().getId()));
        }
        ProductLike like = new ProductLike(id, rating);
        try {
            return repository.save(like);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new LikeChangeFailedException("Error occurred while adding the like");
        }
    }

    @Override
    public ProductLike store(long userId, long productId, short rating) {
        return this.store(new ProductLikeId(new User(userId), new Product(productId)), rating);
    }

    @Override
    public ProductLike update(ProductLikeId id, short rating) {
        this.validateData(id, rating);
        ProductLike like = this.get(id);
        if (like == null) {
            throw new LikeNotFoundException(String
                    .format("can't update like, like from user with id %d on product with id %d don't exists",
                            id.getUser().getId(), id.getProduct().getId()));
        }
        like.setRating(rating);
        try {
            return repository.save(like);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new LikeChangeFailedException("Error occurred while updating the like");
        }
    }

    @Override
    public ProductLike update(long userId, long productId, short rating) {
        return this.update(new ProductLikeId(new User(userId), new Product(productId)), rating);
    }

    @Override
    public void delete(ProductLikeId id) {
        if (!this.exists(id)) {
            throw new LikeNotFoundException(String
                    .format("can't update like, like from user with id %d on product with id %d don't exists",
                            id.getUser().getId(), id.getProduct().getId()));
        }
        try {
            repository.delete(id);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new LikeChangeFailedException("Error occurred while deleting the like");
        }
    }

    @Override
    public void delete(long userId, long productId) {
        this.delete(new ProductLikeId(new User(userId), new Product(productId)));
    }

    @Override
    public Set<ProductLike> getProductLikes(long productId) {
        Product product = productService.get(productId);
        if (product == null) {
            throw new ProductNotFoundException(String.format("can't find product with id %d", productId));
        }
        return product.getLikes();
    }
}
