package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderManager {
    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public boolean deleteOrder(String orderId) {
        return orders.removeIf(order -> order.getId().equals(orderId));
    }

    public Optional<Order> findById(String orderId) {
        return orders.stream().filter(order -> order.getId().equals(orderId)).findFirst();
    }
}
