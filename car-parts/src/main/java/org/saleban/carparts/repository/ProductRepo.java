package org.saleban.carparts.repository;

import org.saleban.carparts.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findProductById(Long id);
}
