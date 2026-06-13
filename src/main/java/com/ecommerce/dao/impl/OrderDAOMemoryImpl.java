package com.ecommerce.dao.impl;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOMemoryImpl implements OrderDAO {
    private final List<Order> store = new ArrayList<>();

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public Optional<Order> findById(String id) {
        return store.stream().filter(order -> order.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Order order) {
        store.add(order);
    }

    @Override
    public void update(Order order) {
        deleteById(order.getId());
        save(order);
    }

    @Override
    public boolean deleteById(String id) {
        return store.removeIf(order -> order.getId().equals(id));
    }
}
