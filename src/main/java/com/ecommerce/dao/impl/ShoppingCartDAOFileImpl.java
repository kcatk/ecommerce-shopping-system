package com.ecommerce.dao.impl;

import com.ecommerce.dao.ShoppingCartDAO;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Path;
import java.util.List;

public class ShoppingCartDAOFileImpl implements ShoppingCartDAO {
    private final Path filePath;

    public ShoppingCartDAOFileImpl(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public ShoppingCart load() {
        List<CartItem> items = JsonUtil.readList(filePath, new TypeToken<>() {
        });
        ShoppingCart cart = new ShoppingCart();
        cart.setItems(items);
        return cart;
    }

    @Override
    public void save(ShoppingCart cart) {
        JsonUtil.writeObject(filePath, cart.getItems());
    }
}
