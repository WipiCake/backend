package com.wipi.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wipi_product_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long productImageId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "original_file_name", nullable = false, length = 255)
    private String originalFileName;

    @Column(name = "saved_file_name", nullable = false, length = 255)
    private String savedFileName;

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_thumbnail", nullable = false)
    private IsThumbnail isThumbnail;

    private ProductImage(Long productId, String originalFileName, String savedFileName, String imagePath, IsThumbnail isThumbnail) {
        this.productId = productId;
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.imagePath = imagePath;
        this.isThumbnail = isThumbnail;
    }

    public static ProductImage of(Long productId, String originalFileName, String savedFileName, String imagePath, IsThumbnail isThumbnail) {
        return new ProductImage(productId, originalFileName, savedFileName, imagePath, isThumbnail);
    }


}

