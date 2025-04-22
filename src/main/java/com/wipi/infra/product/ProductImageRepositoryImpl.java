package com.wipi.infra.product;

import com.wipi.domain.product.ProductImage;
import com.wipi.domain.product.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductImageRepositoryImpl implements ProductImageRepository {

    private final ProductImageJpaRepository productImageJpaRepository;

    @Override
    public void saveAll(List<ProductImage> productImage) {
        productImageJpaRepository.saveAll(productImage);
    }
}
