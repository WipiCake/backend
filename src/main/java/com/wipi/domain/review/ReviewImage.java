package com.wipi.domain.review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wipi_review_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long reviewImageId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "original_file_name", nullable = false, length = 255)
    private String originalFileName;

    @Column(name = "saved_file_name", nullable = false, length = 255)
    private String savedFileName;

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;

    private ReviewImage(Long reviewId, String originalFileName, String savedFileName, String imagePath) {
        this.reviewId = reviewId;
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.imagePath = imagePath;
    }

    public static ReviewImage of(Long reviewId, String originalFileName, String savedFileName, String imagePath) {
        return new ReviewImage( reviewId, originalFileName, savedFileName, imagePath);
    }

}
