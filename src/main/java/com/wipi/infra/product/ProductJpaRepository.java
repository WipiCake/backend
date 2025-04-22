package com.wipi.infra.product;

import com.wipi.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
