package com.ecommerce.dao.impl;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;
import com.ecommerce.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {
    private final Path filePath;

    public ProductDAOImpl(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(JsonUtil.readList(filePath, new TypeToken<>() {
        }));
    }

    @Override
    public Optional<Product> findById(String id) {
        return findAll().stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    @Override
    public void saveAll(List<Product> products) {
        JsonUtil.writeObject(filePath, products);
    }
}
