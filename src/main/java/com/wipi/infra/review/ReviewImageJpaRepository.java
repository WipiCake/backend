package com.wipi.infra.review;

import com.wipi.domain.review.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageJpaRepository extends JpaRepository<ReviewImage, Long> {
}
