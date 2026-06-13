package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private List<OrderItem> items = new ArrayList<>();
    private double totalPrice;
    private String createTime;
    private String status;

    public Order() {
    }

    public Order(String id, List<OrderItem> items, String createTime, String status) {
        this.id = id;
        this.items = items;
        this.createTime = createTime;
        this.status = status;
        this.totalPrice = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
