package com.wipi.infra.product;

import com.wipi.domain.product.IsThumbnail;
import com.wipi.domain.product.ProductImage;
import com.wipi.domain.product.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductImageRepositoryImpl implements ProductImageRepository {

    private final ProductImageJpaRepository productImageJpaRepository;

    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageJpaRepository.save(productImage);
    }

    @Override
    public List<ProductImage> findAll() {
        return productImageJpaRepository.findAll();
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return productImageJpaRepository.findByProductId(productId);
    }
}
