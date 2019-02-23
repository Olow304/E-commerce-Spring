package org.saleban.admin.service;

import org.saleban.admin.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findOne(Long id);

    void delete(Long id);
}
