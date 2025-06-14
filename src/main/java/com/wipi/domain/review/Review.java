package com.wipi.domain.review;

import com.wipi.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wipi_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long productId;

    private String userId;

    @Column(length = 400)
    private String title;

    @Column(length = 999)
    private String content;

    private Long starCount;

    private Review(Long productId, String userId, String title, String content, Long starCount) {
        this.productId = productId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.starCount = starCount;
    }

    private Review(Long reviewId, Long productId, String userId, String title, String content, Long starCount) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.starCount = starCount;
    }

    public static Review of(Long productId, String userId, String title, String content, Long starCount) {
        return new Review(productId, userId, title, content, starCount);
    }

    public static Review of(Long reviewId, Long productId, String userId, String title, String content,Long starCount) {
        return new Review(reviewId,productId,userId,title,content,starCount);
    }

}
