package com.wipi.domain.review;

import java.util.List;

public interface ReviewImageRepository {

    void save(ReviewImage reviewImage);
    List<ReviewImage> findAllByReviewId(Long reviewId);
}
