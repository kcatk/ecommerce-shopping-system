package com.ecommerce.dao;

import com.ecommerce.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    List<Order> findAll();

    Optional<Order> findById(String id);

    void save(Order order);

    void update(Order order);

    boolean deleteById(String id);
}
