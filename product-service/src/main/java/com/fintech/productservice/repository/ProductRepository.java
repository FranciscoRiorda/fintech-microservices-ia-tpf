package com.fintech.productservice.repository;

import com.fintech.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // TODO: Add custom query methods as needed

}
