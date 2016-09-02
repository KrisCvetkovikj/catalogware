package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.catalogware.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Borce on 01.09.2016.
 */
public interface BasketRepository extends JpaRepository<Basket, Long>, JpaSpecificationExecutor<Basket> {
}
