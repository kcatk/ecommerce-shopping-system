package com.ecommerce.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items == null ? new ArrayList<>() : new ArrayList<>(items);
    }

    public void addItem(Product product, int quantity) {
        Optional<CartItem> existing = items.stream()
                .filter(item -> item.getProductId().equals(product.getId()))
                .findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            items.add(new CartItem(product.getId(), product.getName(), product.getPrice(), quantity));
        }
    }

    public boolean removeItem(String productId) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getProductId().equals(productId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void clear() {
        items.clear();
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}
