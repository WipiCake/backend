package com.wipi.domain.product;

import java.util.List;

public interface ProductImageRepository {
    void saveAll(List<ProductImage> productImage);
}
