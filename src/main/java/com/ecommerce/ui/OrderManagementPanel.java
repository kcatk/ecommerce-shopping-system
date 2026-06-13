package com.ecommerce.ui;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderManagementPanel extends JPanel {
    private final OrderService orderService;
    private final DefaultTableModel tableModel;

    public OrderManagementPanel(OrderService orderService, Runnable refreshCart) {
        this.orderService = orderService;
        setLayout(new BorderLayout(8, 8));

        tableModel = new DefaultTableModel(new Object[]{"订单号", "创建时间", "状态", "总价"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createButton = new JButton("生成订单");
        JButton deleteButton = new JButton("删除订单");
        JButton updateButton = new JButton("更新状态");
        JButton refreshButton = new JButton("刷新");

        createButton.addActionListener(e -> {
            try {
                orderService.createOrderFromCart();
                refreshData();
                refreshCart.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                orderService.deleteOrder(tableModel.getValueAt(selected, 0).toString());
                refreshData();
            }
        });

        updateButton.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected < 0) {
                return;
            }
            String orderId = tableModel.getValueAt(selected, 0).toString();
            String status = (String) JOptionPane.showInputDialog(this, "选择订单状态", "订单状态更新",
                    JOptionPane.PLAIN_MESSAGE, null, new String[]{"待支付", "已支付", "已完成"}, "待支付");
            if (status != null) {
                orderService.updateStatus(orderId, status);
                refreshData();
            }
        });

        refreshButton.addActionListener(e -> refreshData());

        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshData();
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        for (Order order : orderService.listOrders()) {
            tableModel.addRow(new Object[]{
                    order.getId(), order.getCreateTime(), order.getStatus(), order.getTotalPrice()
            });
        }
    }
}
