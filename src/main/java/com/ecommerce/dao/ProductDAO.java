package com.ecommerce.dao;

import com.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    List<Product> findAll();

    Optional<Product> findById(String id);

    void saveAll(List<Product> products);
}
