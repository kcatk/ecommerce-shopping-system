package com.ecommerce.ui;

import com.ecommerce.model.CartItem;
import com.ecommerce.service.ShoppingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShoppingCartPanel extends JPanel {
    private final ShoppingService shoppingService;
    private final DefaultTableModel tableModel;
    private final JLabel totalLabel;

    public ShoppingCartPanel(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
        setLayout(new BorderLayout(8, 8));

        tableModel = new DefaultTableModel(new Object[]{"商品ID", "名称", "数量", "单价", "小计"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("添加商品");
        JButton removeButton = new JButton("移除商品");
        JButton clearButton = new JButton("清空购物车");

        addButton.addActionListener(e -> {
            ProductSelectionDialog dialog = new ProductSelectionDialog((Frame) SwingUtilities.getWindowAncestor(this),
                    shoppingService.browseProducts());
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                try {
                    shoppingService.addToCart(dialog.getSelectedProduct().getId(), dialog.getSelectedQuantity());
                    refreshData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected < 0) {
                return;
            }
            String productId = tableModel.getValueAt(selected, 0).toString();
            shoppingService.removeFromCart(productId);
            refreshData();
        });

        clearButton.addActionListener(e -> {
            shoppingService.clearCart();
            refreshData();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);

        totalLabel = new JLabel();
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.WEST);
        southPanel.add(totalLabel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);

        refreshData();
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        for (CartItem item : shoppingService.getCart().getItems()) {
            tableModel.addRow(new Object[]{
                    item.getProductId(), item.getProductName(), item.getQuantity(),
                    item.getUnitPrice(), item.getSubtotal()
            });
        }
        totalLabel.setText("总价: " + shoppingService.getCart().getTotalPrice());
    }
}
