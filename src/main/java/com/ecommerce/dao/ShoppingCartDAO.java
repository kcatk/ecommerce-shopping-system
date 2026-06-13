package com.ecommerce.dao;

import com.ecommerce.model.ShoppingCart;

public interface ShoppingCartDAO {
    ShoppingCart load();

    void save(ShoppingCart cart);
}
