package com.ecommerce.dao.impl;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderDAOFileImplTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldSaveUpdateAndDeleteOrder() {
        OrderDAOFileImpl dao = new OrderDAOFileImpl(tempDir.resolve("orders.json"));
        Order order = new Order("O001", List.of(new OrderItem("P001", "鼠标", 100, 2)), "2026-01-01 12:00:00", "待支付");

        dao.save(order);
        assertEquals(1, dao.findAll().size());

        order.setStatus("已支付");
        dao.update(order);
        assertEquals("已支付", dao.findById("O001").orElseThrow().getStatus());

        assertTrue(dao.deleteById("O001"));
        assertTrue(dao.findAll().isEmpty());
    }
}
