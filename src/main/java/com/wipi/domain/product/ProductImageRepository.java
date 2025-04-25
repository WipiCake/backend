package com.wipi.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository {
    ProductImage save (ProductImage productImage);
    List<ProductImage> findAll();
    List<ProductImage> findByProductId(Long productId);
}
