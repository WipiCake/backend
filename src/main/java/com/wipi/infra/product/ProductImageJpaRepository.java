package com.wipi.infra.product;

import com.wipi.domain.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageJpaRepository extends JpaRepository<ProductImage, Long> {
}
