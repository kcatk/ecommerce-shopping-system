package com.ecommerce.service;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.dao.ShoppingCartDAO;
import com.ecommerce.model.Product;
import com.ecommerce.model.ShoppingCart;

import java.util.List;

public class ShoppingService {
    private final ProductDAO productDAO;
    private final ShoppingCartDAO shoppingCartDAO;
    private final ShoppingCart cart;

    public ShoppingService(ProductDAO productDAO, ShoppingCartDAO shoppingCartDAO) {
        this.productDAO = productDAO;
        this.shoppingCartDAO = shoppingCartDAO;
        this.cart = shoppingCartDAO.load();
    }

    public List<Product> browseProducts() {
        return productDAO.findAll();
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void addToCart(String productId, int quantity) {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在: " + productId));
        if (quantity <= 0) {
            throw new IllegalArgumentException("商品数量必须大于 0");
        }
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("库存不足");
        }
        cart.addItem(product, quantity);
        shoppingCartDAO.save(cart);
    }

    public boolean removeFromCart(String productId) {
        boolean removed = cart.removeItem(productId);
        shoppingCartDAO.save(cart);
        return removed;
    }

    public void clearCart() {
        cart.clear();
        shoppingCartDAO.save(cart);
    }
}
