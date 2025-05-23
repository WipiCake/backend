package com.wipi.infra.review;

import com.wipi.domain.review.Review;
import com.wipi.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public void save(Review review) {
        reviewJpaRepository.save(review);
    }
}
