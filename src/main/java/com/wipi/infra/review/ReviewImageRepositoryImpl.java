package com.wipi.infra.review;

import com.wipi.domain.review.ReviewImage;
import com.wipi.domain.review.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements ReviewImageRepository {

    private final ReviewImageJpaRepository reviewImageJpaRepository;

    @Override
    public void save(ReviewImage reviewImage) {
        reviewImageJpaRepository.save(reviewImage);
    }

    @Override
    public List<ReviewImage> findAllByReviewId(Long reviewId) {
        return reviewImageJpaRepository.findAllByReviewId(reviewId);
    }

    @Override
    public void deleteAllByReviewId(Long reviewId) {
        reviewImageJpaRepository.deleteAllByReviewId(reviewId);
    }
}
