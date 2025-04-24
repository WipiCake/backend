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
    public Optional<ProductImage> findByProductIdAndIsThumbnail(Long productId, IsThumbnail isThumbnail) {
        return Optional.empty();
    }

    @Override
    public void saveAll(List<ProductImage> productImage) {
        productImageJpaRepository.saveAll(productImage);
    }
}
