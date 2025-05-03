package com.wipi.infra.product;

import com.wipi.domain.product.Product;
import com.wipi.domain.product.ProductSellingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(Long productId);

    List<Product> findAllBySellStatus(ProductSellingStatus sellStatus);
}
