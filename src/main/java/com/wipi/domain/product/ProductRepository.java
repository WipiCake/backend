package com.wipi.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findByProductId(Long productId);
    List<Product> findAllBySellStatus(ProductSellingStatus sellStatus);
}
