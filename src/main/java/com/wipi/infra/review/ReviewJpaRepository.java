package com.wipi.infra.review;

import com.wipi.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {


}
