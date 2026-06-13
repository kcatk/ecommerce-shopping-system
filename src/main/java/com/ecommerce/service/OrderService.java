package com.ecommerce.service;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.util.DateUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final ShoppingService shoppingService;

    public OrderService(OrderDAO orderDAO, ProductDAO productDAO, ShoppingService shoppingService) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.shoppingService = shoppingService;
    }

    public Order createOrderFromCart() {
        ShoppingCart cart = shoppingService.getCart();
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("购物车为空，无法生成订单");
        }

        List<Product> products = productDAO.findAll();
        for (CartItem item : cart.getItems()) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("商品不存在: " + item.getProductId()));
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalStateException("库存不足: " + product.getName());
            }
            product.setStock(product.getStock() - item.getQuantity());
        }

        List<OrderItem> items = cart.getItems().stream()
                .map(it -> new OrderItem(it.getProductId(), it.getProductName(), it.getUnitPrice(), it.getQuantity()))
                .collect(Collectors.toList());

        Order order = new Order(generateOrderId(), items, DateUtil.now(), "待支付");
        orderDAO.save(order);
        productDAO.saveAll(products);
        shoppingService.clearCart();
        return order;
    }

    public List<Order> listOrders() {
        return orderDAO.findAll();
    }

    public boolean deleteOrder(String orderId) {
        return orderDAO.deleteById(orderId);
    }

    public void updateStatus(String orderId, String status) {
        Order order = orderDAO.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("订单不存在: " + orderId));
        order.setStatus(status);
        orderDAO.update(order);
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
