package org.saleban.carparts.service.impl;

import org.saleban.carparts.domain.Product;
import org.saleban.carparts.repository.ProductRepo;
import org.saleban.carparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepol;

    @Override
    public List<Product> findAll() {
        return productRepol.findAll();
    }

    @Override
    public Optional<Product> findOne(Long id) {
        return productRepol.findById(id);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepol.findProductById(id);
    }
}
