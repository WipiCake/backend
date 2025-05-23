package com.wipi.infra.review;

import com.wipi.domain.review.Review;
import com.wipi.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public void save(Review review) {
        reviewJpaRepository.save(review);
    }

    @Override
    public List<Review> findAllByProductId(Long productId) {
        return reviewJpaRepository.findAllByProductId(productId);
    }
}
