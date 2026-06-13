package com.ecommerce.service;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.dao.ShoppingCartDAO;
import com.ecommerce.dao.impl.OrderDAOFileImpl;
import com.ecommerce.dao.impl.ProductDAOImpl;
import com.ecommerce.dao.impl.ShoppingCartDAOFileImpl;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldCreateOrderAndPersistStockOrderAndCart() {
        Path productFile = tempDir.resolve("products.json");
        Path orderFile = tempDir.resolve("orders.json");
        Path cartFile = tempDir.resolve("shopping_cart.json");

        ProductDAO productDAO = new ProductDAOImpl(productFile);
        productDAO.saveAll(List.of(new Product("P001", "笔记本", 5000, 5)));
        OrderDAO orderDAO = new OrderDAOFileImpl(orderFile);
        ShoppingCartDAO cartDAO = new ShoppingCartDAOFileImpl(cartFile);

        ShoppingService shoppingService = new ShoppingService(productDAO, cartDAO);
        shoppingService.addToCart("P001", 2);

        OrderService orderService = new OrderService(orderDAO, productDAO, shoppingService);
        Order order = orderService.createOrderFromCart();

        assertNotNull(order);
        assertEquals(1, orderService.listOrders().size());
        assertEquals(3, productDAO.findById("P001").orElseThrow().getStock());
        assertTrue(shoppingService.getCart().getItems().isEmpty());
        assertFalse(order.getId().isBlank());
    }
}
