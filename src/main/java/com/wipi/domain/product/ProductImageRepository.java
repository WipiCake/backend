package com.wipi.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository {
    void saveAll(List<ProductImage> productImage);
    Optional<ProductImage> findByProductIdAndIsThumbnail(Long productId, IsThumbnail isThumbnail);
}
