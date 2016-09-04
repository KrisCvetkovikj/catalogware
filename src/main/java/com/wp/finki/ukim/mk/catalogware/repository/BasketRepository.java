package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.catalogware.model.Basket;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.w3c.dom.stylesheets.LinkStyle;

/**
 * Created by Borce on 01.09.2016.
 */
public interface BasketRepository extends JpaRepository<Basket, Long>, JpaSpecificationExecutor<Basket> {

    Basket findByIdAndProductsId(long id, long productId);
}
