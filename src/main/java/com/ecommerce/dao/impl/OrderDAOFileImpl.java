package com.ecommerce.dao.impl;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOFileImpl implements OrderDAO {
    private final Path filePath;

    public OrderDAOFileImpl(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(JsonUtil.readList(filePath, new TypeToken<>() {
        }));
    }

    @Override
    public Optional<Order> findById(String id) {
        return findAll().stream().filter(order -> order.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Order order) {
        List<Order> orders = findAll();
        orders.add(order);
        JsonUtil.writeObject(filePath, orders);
    }

    @Override
    public void update(Order order) {
        List<Order> orders = findAll();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                orders.set(i, order);
                JsonUtil.writeObject(filePath, orders);
                return;
            }
        }
    }

    @Override
    public boolean deleteById(String id) {
        List<Order> orders = findAll();
        boolean removed = orders.removeIf(order -> order.getId().equals(id));
        if (removed) {
            JsonUtil.writeObject(filePath, orders);
        }
        return removed;
    }
}
