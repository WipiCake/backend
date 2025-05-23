package com.wipi.domain.review;

import java.util.List;

public interface ReviewRepository {
    void save(Review review);
    List<Review> findAllByProductId(Long productId);
}
