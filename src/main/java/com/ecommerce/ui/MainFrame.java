package com.ecommerce.ui;

import com.ecommerce.service.OrderService;
import com.ecommerce.service.ShoppingService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(ShoppingService shoppingService, OrderService orderService) {
        super("电商购物系统");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        ShoppingCartPanel cartPanel = new ShoppingCartPanel(shoppingService);
        OrderManagementPanel orderPanel = new OrderManagementPanel(orderService, cartPanel::refreshData);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("购物车管理", cartPanel);
        tabbedPane.addTab("订单管理", orderPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
