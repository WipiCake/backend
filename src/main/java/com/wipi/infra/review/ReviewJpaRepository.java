package com.wipi.infra.review;

import com.wipi.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {


    List<Review> findAllByProductId(Long productId);
}
