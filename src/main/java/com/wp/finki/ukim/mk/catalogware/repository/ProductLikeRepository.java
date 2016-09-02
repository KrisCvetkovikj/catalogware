package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.ProductLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Borce on 01.09.2016.
 */
public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeId>,
        JpaSpecificationExecutor<ProductLike> {

}
