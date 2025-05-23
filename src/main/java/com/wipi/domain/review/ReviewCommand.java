package com.wipi.domain.review;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.RegisteredSynchronization;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCommand {

    @Getter
    public static class Register{
        private final Long productId;
        private final String userId;
        private final String title;
        private final String content;
        private final Long starCount;
        private final List<MultipartFile> images;

        private Register(Long productId, String userId, String title, String content, Long starCount, List<MultipartFile> images) {
            this.productId = productId;
            this.userId = userId;
            this.title = title;
            this.content = content;
            this.starCount = starCount;
            this.images = images;
        }

        public static Register of (Long productId, String userId, String title, String content, Long starCount, List<MultipartFile> images) {
            return new Register(productId, userId, title, content, starCount, images);
        }
    }

    @Getter
    public static class Update{
        private final Long reviewId;
        private final Long productId;
        private final String userId;
        private final String title;
        private final String content;
        private final Long starCount;
        private final List<MultipartFile> images;

        private Update(Long reviewId, Long productId, String userId, String title, String content, Long starCount, List<MultipartFile> images) {
            this.reviewId = reviewId;
            this.productId = productId;
            this.userId = userId;
            this.title = title;
            this.content = content;
            this.starCount = starCount;
            this.images = images;
        }

        public static Update of (Long reviewId, Long productId, String userId, String title, String content, Long starCount, List<MultipartFile> images) {
            return new Update(reviewId, productId, userId, title, content, starCount, images);
        }
    }
}
