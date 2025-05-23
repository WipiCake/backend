package com.wipi.domain.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResult {

    @Getter
    public static class GetAll{
        private final Long reviewId;
        private final String userId;
        private final String title;
        private final String content;
        private final Long starCount;
        private final List<String> images;

        private GetAll(Long reviewId, String userId, String title, String content, Long starCount, List<String> images) {
            this.reviewId = reviewId;
            this.userId = userId;
            this.title = title;
            this.content = content;
            this.starCount = starCount;
            this.images = images;
        }

        public static GetAll of(Long reviewId, String userId, String title, String content, Long starCount, List<String> images) {
            return new GetAll(reviewId, userId, title, content, starCount, images);
        }
    }



}
