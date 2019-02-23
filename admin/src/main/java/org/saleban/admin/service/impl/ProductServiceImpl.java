package org.saleban.admin.service.impl;

import org.saleban.admin.domain.Product;
import org.saleban.admin.repository.ProductRepo;
import org.saleban.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> findOne(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }
}
