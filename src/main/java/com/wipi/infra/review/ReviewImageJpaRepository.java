package com.wipi.infra.review;

import com.wipi.domain.review.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageJpaRepository extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findAllByReviewId(Long reviewId);

    void deleteAllByReviewId(Long reviewId);
}
