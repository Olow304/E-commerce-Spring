package org.saleban.carparts.service;

import org.saleban.carparts.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findOne(Long id);

    Product findProductById(Long id);
}
