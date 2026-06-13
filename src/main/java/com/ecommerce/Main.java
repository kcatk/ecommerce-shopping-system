package com.ecommerce;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.dao.ShoppingCartDAO;
import com.ecommerce.dao.impl.OrderDAOFileImpl;
import com.ecommerce.dao.impl.ProductDAOImpl;
import com.ecommerce.dao.impl.ShoppingCartDAOFileImpl;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ShoppingService;
import com.ecommerce.ui.MainFrame;

import javax.swing.*;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAOImpl(Path.of("data/products.json"));
        OrderDAO orderDAO = new OrderDAOFileImpl(Path.of("data/orders.json"));
        ShoppingCartDAO cartDAO = new ShoppingCartDAOFileImpl(Path.of("data/shopping_cart.json"));

        ShoppingService shoppingService = new ShoppingService(productDAO, cartDAO);
        OrderService orderService = new OrderService(orderDAO, productDAO, shoppingService);

        SwingUtilities.invokeLater(() -> new MainFrame(shoppingService, orderService).setVisible(true));
    }
}
