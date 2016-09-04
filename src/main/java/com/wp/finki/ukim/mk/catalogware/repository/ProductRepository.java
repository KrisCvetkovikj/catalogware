package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.catalogware.model.Category;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Borce on 31.08.2016.
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    long countByBasketsId(long basketId);

    List<Product> findDistinctByCategoriesNameIn(String... category);

    List<Product> findDistinctByCategoriesNameIn(Pageable pageable, String... category);

    List<Product> findByOrdersId(long orderId);
}
