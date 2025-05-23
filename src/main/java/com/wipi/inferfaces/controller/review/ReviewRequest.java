package com.wipi.inferfaces.controller.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewRequest {

    @Getter
    public static class Register {

        @NotNull(message = "상품 ID는 필수입니다.")
        @Positive(message = "상품 ID는 0보다 커야 합니다.")
        @Schema(description = "리뷰 대상 상품 ID", example = "101")
        private final Long productId;

        @NotBlank(message = "리뷰 제목은 필수입니다.")
        @Schema(description = "리뷰 제목", example = "정말 좋아요!")
        private final String title;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        @Schema(description = "리뷰 내용", example = "배송도 빠르고 품질도 좋네요.")
        private final String content;

        @NotNull(message = "별점은 필수입니다.")
        @Positive(message = "별점은 1 이상이어야 합니다.")
        @Schema(description = "별점 (1~5)", example = "5")
        private final Long starCount;


        private Register(Long productId, String title, String content, Long starCount) {
            this.productId = productId;
            this.title = title;
            this.content = content;
            this.starCount = starCount;
        }

        public static Register of (Long productId, String title, String content, Long starCount) {
            return new Register(productId, title, content, starCount);
        }
    }

}
