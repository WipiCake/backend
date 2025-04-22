package com.wipi.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_thumbnail", nullable = false)
    private IsThumbnail isThumbnail;

    private ProductImage(Long productId, String imageName, String imagePath, IsThumbnail isThumbnail) {
        this.productId = productId;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.isThumbnail = isThumbnail;
    }

    public static List<ProductImage> create(List<ProductCommand.RegisterImage> imageCommands, Long productId) {
        return imageCommands.stream()
                .map(img -> new ProductImage(productId, img.getImageName(), img.getImagePath(), img.getIsThumbnail()))
                .collect(Collectors.toList());
    }
}

