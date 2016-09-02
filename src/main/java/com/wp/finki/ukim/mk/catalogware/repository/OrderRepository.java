package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.catalogware.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Borce on 01.09.2016.
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}
