package com.prysh.product_service.repository;

import com.prysh.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Boolean existsBySku(String sku);
    Boolean existsByStuAndIdNot(String sku,Long id);
}
