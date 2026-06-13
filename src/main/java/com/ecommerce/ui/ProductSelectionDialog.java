package com.ecommerce.ui;

import com.ecommerce.model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductSelectionDialog extends JDialog {
    private final JComboBox<Product> productComboBox;
    private final JSpinner quantitySpinner;
    private boolean confirmed;

    public ProductSelectionDialog(Frame owner, List<Product> products) {
        super(owner, "添加商品", true);
        setLayout(new GridLayout(3, 2, 8, 8));

        add(new JLabel("商品:"));
        productComboBox = new JComboBox<>(products.toArray(new Product[0]));
        productComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value.getName() + " (库存:" + value.getStock() + ")"));
        add(productComboBox);

        add(new JLabel("数量:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        add(quantitySpinner);

        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> setVisible(false));
        add(okButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Product getSelectedProduct() {
        return (Product) productComboBox.getSelectedItem();
    }

    public int getSelectedQuantity() {
        return (Integer) quantitySpinner.getValue();
    }
}
